package com.example.cinemaplanner.event.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.*;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.event.repository.GenreRepository;
import com.example.cinemaplanner.event.repository.LearningRepository;
import com.example.cinemaplanner.event.repository.SearchRepository;
import com.example.cinemaplanner.team.model.Team;
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
 * Created by Kevin on 13/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final EventRepository eventRepository;
    private final SearchRepository searchRepository;
    private final LearningRepository learningRepository;
    private final AuthenticationManager authenticationManager;
    private final GenreRepository genreRepository;
    private final long minDate = 1400000000000L;


    @Autowired
    public EventController(TeamRepository teamRepository, AccountService accountService, EventRepository eventRepository, SearchRepository searchRepository, LearningRepository learningRepository, AuthenticationManager authenticationManager, GenreRepository genreRepository) {
        this.teamRepository = teamRepository;
        this.accountService = accountService;
        this.eventRepository = eventRepository;
        this.searchRepository = searchRepository;
        this.learningRepository = learningRepository;
        this.authenticationManager = authenticationManager;
        this.genreRepository = genreRepository;
    }

    @RequestMapping(value = "create", method = POST)
    public EventPublic createEvent(@RequestHeader(value = "token") String token, @RequestBody EventCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getEnd() < minDate || info.getStart() < minDate || info.getName().isEmpty()) {
            throw new Exception("Wrong parameters");
        } else {
            if (account != null) {
                System.out.println("coucou");
                for (Team team :
                        account.getTeams()) {
                    System.out.println("coucou2");
                    if (team.getId() == info.getIdTeam()) {
                        Event event = new Event();
                        event.setName(info.getName());
                        event.setDtend(info.getEnd());
                        event.setDtstart(info.getStart());
                        event.setCreator(account.getFirstName());
                        event.setPostComment(new ArrayList<>());
                        event.setPreComment(new ArrayList<>());
                        event.setDescription(searchRepository.findById(info.getIdMovie()));
                        if (event.getDescription() == null) {
                            System.out.println(event.getDescription());
                            throw new Exception("No movie selected");
                        }
                        eventRepository.save(event);
                        List<Event> events = team.getEvents();
                        events.add(event);
                        team.setEvents(events);
                        teamRepository.save(team);
                        return new EventPublic(event);
                    }
                }
                throw new Exception("Wrong team id");
            } else {
                throw new AccountNotFoundException();
            }
        }
    }

    @RequestMapping(value = "update", method = POST)
    public EventPublic updateEvent(@RequestHeader(value = "token") String token, @RequestBody EventUpdate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);


        if (account != null) {
            Event event = eventRepository.findById(info.getId());

            if (event != null) {
                if (info.getEnd() < minDate && info.getStart() < minDate && info.getName().isEmpty()) {
                    return new EventPublic(event);
                }
                if (!info.getName().isEmpty()) {
                    event.setName(info.getName());
                }
                if (info.getStart() > minDate) {
                    event.setDtstart(info.getStart());
                }
                if (info.getEnd() > minDate) {
                    event.setDtend(info.getEnd());
                }
                event.setDescription(searchRepository.findById(info.getIdMovie()));
                eventRepository.save(event);
                return new EventPublic(event);
            }
            throw new Exception("Event not found");
        } else {
            throw new AccountNotFoundException();
        }
    }

    @RequestMapping(value = "search", method = POST)
    public List<Movie> callApi(@RequestHeader(value = "token") String token, @RequestBody EventQuery query) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);

        List<Movie> movies = new ArrayList<>();

        if (account != null) {
            final String uri = "https://api.themoviedb.org/3/search/movie?api_key=8c04432a7ef30c6867723b9f144916e8&language=fr-FR&query=" + query.getQuery();

            String link = "https://image.tmdb.org/t/p/w500";
            System.out.println(query.getQuery());
            System.out.println(uri);
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


            JsonSearchPage searchPage = restTemplate.getForObject(uri, JsonSearchPage.class);


            for (JsonSearchResult res :
                    searchPage.getResults()) {
                res.setBackdrop_path(link + res.getBackdrop_path());
                res.setPoster_path(link + res.getPoster_path());
                Movie movie = new Movie(res);
                movies.add(movie);
                searchRepository.save(movie);

            }


            return movies;
        } else {
            throw new AccountNotFoundException();
        }

    }

    @RequestMapping(value = "movies", method = GET)
    public List<Movie> machineLearning() {
        return searchRepository.findAll();
    }

    @RequestMapping(value = "delete", method = POST)
    public boolean deleteEvent(@RequestHeader(value = "token") String token, @RequestBody EventDelete info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);

        if (account != null) {
            List<Event> newEvents = new ArrayList<>();
            for (Team team :
                    account.getTeams()) {
                if (team.getId() == info.getIdTeam()) {
                    List<Event> events = team.getEvents();
                    for (Event ev : events) {
                        if (ev.getId() != info.getIdEvent()) {
                            newEvents.add(ev);
                        }
                    }
                    team.setEvents(newEvents);
                    teamRepository.save(team);
                    return true;
                }
            }
            throw new Exception("Wrong team id");
        } else {
            throw new AccountNotFoundException();
        }

    }

    public void test(int teamId) {


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
            }


            Instances isTrainingSet = new Instances("training", atts, 10);
            isTrainingSet.setClassIndex(3);


            for (int i = 0; i < resultBis.size(); i++) {
                if (resultBis.get(i).equals("yes")) {
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
            }

            List<String> genreNew1 = new ArrayList<>();
            List<String> genreNew2 = new ArrayList<>();
            List<String> genreNew3 = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();
            String date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH);
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
                }
                if (m.getGenre_ids().size() > 1) {
                    genreNew2.add(String.valueOf(m.getGenre_ids().get(1)));
                }
                if (m.getGenre_ids().size() > 2) {
                    genreNew3.add(String.valueOf(m.getGenre_ids().get(2)));
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

            try {
                NaiveBayes classifier = new NaiveBayes();
                classifier.buildClassifier(isTrainingSet);


                System.out.println("");
                System.out.println("");
                System.out.println("");
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
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }
}
