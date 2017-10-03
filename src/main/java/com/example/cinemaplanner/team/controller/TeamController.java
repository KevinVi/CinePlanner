package com.example.cinemaplanner.team.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.*;
import com.example.cinemaplanner.event.repository.GenreRepository;
import com.example.cinemaplanner.event.repository.LearningRepository;
import com.example.cinemaplanner.event.repository.SearchRepository;
import com.example.cinemaplanner.mail.MailService;
import com.example.cinemaplanner.team.model.*;
import com.example.cinemaplanner.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final MailService emailService;
    private final GenreRepository genreRepository;
    private final LearningRepository learningRepository;
    private final SearchRepository searchRepository;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public TeamController(TeamRepository teamRepository, AccountService accountService, MailService emailService, GenreRepository genreRepository, LearningRepository learningRepository, SearchRepository searchRepository, AuthenticationManager authenticationManager) {
        this.teamRepository = teamRepository;
        this.accountService = accountService;
        this.emailService = emailService;
        this.genreRepository = genreRepository;
        this.learningRepository = learningRepository;
        this.searchRepository = searchRepository;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public TeamPublic createTeam(@RequestHeader(value = "token") String token, @RequestBody TeamCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getName().isEmpty()) {
            throw new Exception("Can't be empty");
        }
        if (account != null) {
            System.out.println(account.getTeams().toString());
            for (Team g :
                    account.getTeams()) {
                if (g.getName().equals(info.getName())) {
                    if (g.getCreator().equals(account.getLogin())) {
                        throw new Exception("You have a team with this name already");
                    }
                }
            }
            Team team = new Team();
            team.setName(info.getName());
            team.setCreator(account.getLogin());
            if (info.getUsers() != null) {
                team.setPendingUsers(info.getUsers());
            }
            account.getTeams().add(team);

            if (info.getUsers() != null) {
                for (String user : info.getUsers()) {
                    System.out.println(info.getUsers());
                    Account guest = accountService.getAccountByLogin(user);
                    if (guest != null) {
                        if (guest.getId() != account.getId()) {
                            emailService.addToTeam(guest.getLogin(), "www.google.com");
                        }
                    } else {
                        //send mail to create and invite using creator and name
                        emailService.addToTeamNewAccount(user, "www.google.com");
                    }
                }
            }
            teamRepository.save(team);
            accountService.saveAccount(account);

            System.out.println(team);
            return new TeamPublic(team);
        } else {
            throw new MustBeAuthenticatedException();
        }

    }

    @RequestMapping(value = "leave", method = POST)
    public boolean leaveTeam(@RequestHeader(value = "token") String token, @RequestBody TeamId id) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            for (Team g :
                    account.getTeams()) {
                if (g.getId() == id.getId()) {
                    account.getTeams().remove(g);
                    accountService.saveAccount(account);
                    return true;
                }
            }
            return false;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "learning", method = POST)
    public List<Movie> requestLearning(@RequestHeader(value = "token") String token, @RequestBody TeamId id) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {

            List<Movie> movies = searchRepository.findAll();
            List<Learning> learnings = learningRepository.findByTeamId(id.getId());
            if (learnings != null) {
                movies.subList(learnings.size(), 11);
            } else {
                movies = movies.subList(0, 11);
            }
            return movies;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "learning/suggestion", method = POST)
    public Movie suggestionLearning(@RequestHeader(value = "token") String token, @RequestBody TeamId id) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<Learning> learnings = learningRepository.findByTeamId(id.getId());
            System.out.println(learnings);
            if (learnings != null) {
                if (!learnings.isEmpty()) {
                    Movie movie = test(id.getId());
                    if (movie != null) {
                        return movie;
                    } else {
                        throw new Exception("Can't learn");
                    }
                }
            }
            return new Movie();
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "learning/result", method = POST)
    public boolean resultLearning(@RequestHeader(value = "token") String token, @RequestBody LearningBody body) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            if (body.getContent() != null) {
                if (!body.getContent().isEmpty()) {
                    System.out.println("bodysize ->" + body.getContent().size());
                    for (LearningContent content :
                            body.getContent()) {
                        Movie movie = searchRepository.findById(content.getIdMovie());
                        System.out.println("Movie : " + movie);
                        if (movie != null) {
                            Learning learning = new Learning();
                            learning.setAnswer(String.valueOf(content.isLiked()).toLowerCase());
                            learning.setMovieId(movie.getId());
                            learning.setTeamId(content.getIdTeam());
                            if (!movie.getGenre_ids().isEmpty()) {
                                learning.setGender1(String.valueOf(movie.getGenre_ids().get(0)));
                            }
                            if (movie.getGenre_ids().size() > 1) {
                                learning.setGender2(String.valueOf(movie.getGenre_ids().get(1)));
                            }
                            if (movie.getGenre_ids().size() > 2) {
                                learning.setGender3(String.valueOf(movie.getGenre_ids().get(2)));
                            }
                            learningRepository.save(learning);
                        }
                    }
                    System.out.println(learningRepository.findByTeamId(body.getContent().get(0).getIdTeam()));
                    return true;
                } else {
                    throw new Exception("Content is empty");
                }
            } else {
                throw new Exception("Wrong Parameters");
            }
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "teams", method = POST)
    public List<TeamPublic> getTeams(@RequestHeader(value = "token") String token) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<TeamPublic> teamPublics = new ArrayList<>();
            for (Team g :
                    account.getTeams()) {
                teamPublics.add(new TeamPublic(g));
            }
            return teamPublics;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "invite", method = POST)
    public boolean inviteToTeam(@RequestHeader(value = "token") String token, @RequestBody TeamContent invite) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Team team = teamRepository.findById(invite.getTeamId());
            if (team!=null){
                for (Team t :
                        account.getTeams()) {
                    if (t.getId() == team.getId()){
                        if (invite.getString() != null) {
                            Account guest = accountService.getAccountByLogin(invite.getString());
                            if (guest != null) {
                                if (guest.getId() != account.getId()) {
                                    emailService.addToTeam(guest.getLogin(), "https://salty-plateau-71086.herokuapp.com/");
                                }
                            } else {
                                //send mail to create and invite using creator and name
                                emailService.addToTeamNewAccount(invite.getString(), "https://salty-plateau-71086.herokuapp.com/");
                            }
                            team.getPendingUsers().add(invite.getString());
                            teamRepository.save(team);
                            return true;
                        }
                    }
                }
            }else{
                throw new Exception("Team not found");
            }
            throw new Exception("Wrong Parameters");
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "pending", method = POST)
    public List<TeamPublic> pendingTeams(@RequestHeader(value = "token") String token) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<Team> teams = teamRepository.findByLogin(account.getLogin());
            List<TeamPublic> teamPublics = new ArrayList<>();
            for (Team t :
                    teams) {
                teamPublics.add(new TeamPublic(t));
            }
            return teamPublics;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }


    @RequestMapping(value = "join", method = POST)
    public boolean joinTeams(@RequestHeader(value = "token") String token, @RequestBody TeamId id) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Team team = teamRepository.findById(id.getId());
            if (team.getPendingUsers().contains(account.getLogin())) {
                team.getPendingUsers().remove(account.getLogin());
            } else {
                throw new Exception("you are not in this team");
            }
            account.getTeams().add(team);
            teamRepository.save(team);
            accountService.saveAccount(account);
            return true;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    public Movie test(int teamId) throws Exception {


        Gender genderRepo = genreRepository.findAll().get(0);
        List<Integer> genderInteger = genderRepo.getGenre_ids();
        List<String> gender = new ArrayList<>();
        for (Integer integer :
                genderInteger) {
            gender.add(String.valueOf(integer));
        }
        Attribute genre1 = new Attribute("genre1", gender);

        //declare
        Attribute genre2 = new Attribute("genre2", gender);

        //declare
        Attribute genre3 = new Attribute("genre3", gender);


        // Declare yes / no
        List<String> isGood = new ArrayList<>();
        isGood.add("true");
        isGood.add("false");
        Attribute go = new Attribute("isGood", isGood);

        ArrayList<Attribute> atts = new ArrayList<>();
        atts.add(genre1);
        atts.add(genre2);
        atts.add(genre3);
        atts.add(go);

        List<Learning> pref = learningRepository.findByTeamId(teamId);


        if (pref != null) {
            //petit jeu de donné

            ArrayList<String> genreNumero1Bis = new ArrayList<>();
            ArrayList<String> genreNumero2Bis = new ArrayList<>();
            ArrayList<String> genreNumero3Bis = new ArrayList<>();
            ArrayList<String> resultBis = new ArrayList<>();


            for (Learning l :
                    pref) {
                genreNumero1Bis.add(l.getGender1());
                genreNumero2Bis.add(l.getGender2());
                genreNumero3Bis.add(l.getGender3());
                resultBis.add(l.getAnswer());

                System.out.println("l->" + l.toString());
            }

            System.out.println("pref size->" + pref.size());

            Instances isTrainingSet = new Instances("training", atts, 10);
            isTrainingSet.setClassIndex(3);


            for (int i = 0; i < resultBis.size(); i++) {
                Instance iExample = new DenseInstance(atts.size());
                iExample.setValue(atts.get(0), genreNumero1Bis.get(i));
                if (genreNumero2Bis.get(i).equals("0")) {
                    iExample.setValue(atts.get(1), Utils.missingValue());
                } else {
                    iExample.setValue(atts.get(1), genreNumero2Bis.get(i));
                }
                if (genreNumero3Bis.get(i).equals("0")) {
                    iExample.setValue(atts.get(2), Utils.missingValue());
                } else {
                    iExample.setValue(atts.get(2), genreNumero3Bis.get(i));
                }
                iExample.setValue(atts.get(3), resultBis.get(i));
                isTrainingSet.add(iExample);

            }

            List<String> genreNew1 = new ArrayList<>();
            List<String> genreNew2 = new ArrayList<>();
            List<String> genreNew3 = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();

            String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            System.out.println("My date" + date);


            String uri = "https://api.themoviedb.org/3/discover/movie?api_key=8c04432a7ef30c6867723b9f144916e8&language=fr-FR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_date.gte=" + date;

            String link = "https://image.tmdb.org/t/p/w500";
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
            JsonSearchPage searchPage = restTemplate.getForObject(uri, JsonSearchPage.class);
            List<String> names = new ArrayList<>();

            List<Movie> moviesSuggest = new ArrayList<>();
            for (JsonSearchResult res :
                    searchPage.getResults()) {
                res.setBackdrop_path(link + res.getBackdrop_path());
                res.setPoster_path(link + res.getPoster_path());
                Movie movie = new Movie(res);
                searchRepository.save(movie);
                moviesSuggest.add(movie);
            }
            for (Movie m :
                    moviesSuggest) {
                names.add(m.getTitle());
                if (!m.getGenre_ids().isEmpty()) {
                    genreNew1.add(String.valueOf(m.getGenre_ids().get(0)));
                } else {
                    genreNew1.add("0");
                }
                if (m.getGenre_ids().size() > 1) {
                    genreNew2.add(String.valueOf(m.getGenre_ids().get(1)));
                } else {
                    genreNew2.add("0");
                }
                if (m.getGenre_ids().size() > 2) {
                    genreNew3.add(String.valueOf(m.getGenre_ids().get(2)));
                } else {
                    genreNew3.add("0");
                }
            }

            Instances testSet = new Instances("test", atts, 10);

            for (int i = 0; i < genreNew1.size(); i++) {
                Instance iExample = new DenseInstance(atts.size());
                iExample.setValue(atts.get(0), genreNew1.get(i));
                if (genreNew2.get(i).equals("0")) {
                    iExample.setValue(atts.get(1), Utils.missingValue());
                } else {
                    iExample.setValue(atts.get(1), genreNew2.get(i));
                }
                if (genreNew3.get(i).equals("0")) {
                    iExample.setValue(atts.get(2), Utils.missingValue());
                } else {
                    iExample.setValue(atts.get(2), genreNew3.get(i));
                }
                iExample.setValue(atts.get(3), Utils.missingValue());
                testSet.add(iExample);
            }

            testSet.setClassIndex(testSet.numAttributes() - 1);

            List<Movie> toSuggest = new ArrayList<>();
            try {
                NaiveBayes classifier = new NaiveBayes();
                classifier.buildClassifier(isTrainingSet);


                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("training set" + isTrainingSet.toString());
                for (int i = 0; i < testSet.size(); i++) {

                    double label = classifier.classifyInstance(testSet.instance(i));
                    double[] predictionDistribution = classifier.distributionForInstance(testSet.instance(i));
                    testSet.instance(i).setClassValue(label);
                    System.out.println(names.get(i)
                            + " : " + testSet.get(i).toString(0)
                            + " : " + testSet.get(i).toString(1)
                            + " : " + testSet.get(i).toString(2)
                            + " : " + testSet.instance(i).toString(3)
                            + " : " + Double.toString(predictionDistribution[0]));
                    if (testSet.instance(i).toString(3).equals("true")) {
                        toSuggest.add(moviesSuggest.get(i));
                    }
                }
                Movie movieToDisplay = null;
                for (Movie m :
                        toSuggest) {
                    boolean isCotained = false;
                    for (Learning l :
                            pref) {
                        if (m.getId() == l.getMovieId()) {
                            isCotained = true;
                        }
                    }
                    if (!isCotained) {
                        movieToDisplay = m;
                        break;
                    }
                }
                return movieToDisplay;


            } catch (Exception ex) {
                throw new Exception("Can't predict");
            }


        } else {
            throw new Exception("Learning not complete");
        }
    }

}
