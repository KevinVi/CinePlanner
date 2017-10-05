package com.example.cinemaplanner.config;

import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.json.JsonGender;
import com.example.cinemaplanner.event.model.json.JsonGenderArray;
import com.example.cinemaplanner.event.model.json.JsonSearchPage;
import com.example.cinemaplanner.event.model.learning.Gender;
import com.example.cinemaplanner.event.model.learning.Movie;
import com.example.cinemaplanner.event.repository.GenreRepository;
import com.example.cinemaplanner.event.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kevin on 09/07/2017 for CinePlanner for CinePlanner.
 * Create at least one account when the server start
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final AccountService accountService;
    private final SearchRepository searchRepository;
    private final GenreRepository genreRepository;


    @Autowired
    public DataLoader(AccountService accountService, SearchRepository searchRepository, GenreRepository genreRepository) {
        this.accountService = accountService;
        this.searchRepository = searchRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * insert admin account if not existed
     *
     * @param args args
     */
    public void run(ApplicationArguments args) {
        if (accountService.getAccounts().isEmpty()) {
            Account account = Account.builder().id(0).login("kevin.vivor@gmail.com").password("cXNH44GxgG").firstName("kevin").lastName("vivor").build();
            accountService.updateAccount(account);

//            List<Integer> genreArrival = new ArrayList<>();
//            Movie arrival = Movie.builder()
//                    .vote_count(1314)
//                    .id(346364)
//                    .vote_average(7.5f)
//                    .title("Ça")
//                    .popularity(854.434398f)
//                    .poster_path("/3SUz0F0I2Bodcj9Ev2pYSWnE9zp.jpg")
//                    .original_language("en")
//                    .original_title("It")
//                    .backdrop_path("/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg")
//                    .overview("À Derry, dans le Maine, sept gamins ayant du mal à s'intégrer se sont regroupés au sein du \"Club des Ratés\". Rejetés par leurs camarades, ils sont les cibles favorites des gros durs de l'école. Ils ont aussi en commun d'avoir éprouvé leur plus grande terreur face à un terrible prédateur métamorphe qu'ils appellent \"Ça\"…")
//                    .release_date("2017-09-05")
//                    .genre_ids()

            List<String> queriesTest = new ArrayList<>();
            queriesTest.add("Arrival");
            queriesTest.add("Frozen");
            queriesTest.add("Gravity");
            queriesTest.add("It");
            queriesTest.add("The Wolf of Wall Street");
            queriesTest.add("World War Z");
            queriesTest.add("Shutter Island");
            queriesTest.add("Star Wars, épisode VII - le Réveil de la Force");
            queriesTest.add("Avengers: Age of Ultron");
            queriesTest.add("La La Land");
            queriesTest.add("Gone Girl");
            final String uri = "https://api.themoviedb.org/3/search/movie?api_key=8c04432a7ef30c6867723b9f144916e8&language=fr-FR&query=";
            final String genderUri = "https://api.themoviedb.org/3/genre/movie/list?api_key=8c04432a7ef30c6867723b9f144916e8&language=fr-FR";
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

            JsonGender gender = restTemplate.getForObject(genderUri, JsonGender.class);

            List<Integer> genreIds = new ArrayList<>();
            Gender gender1 = new Gender();
            for (JsonGenderArray jg :
                    gender.getGenres()) {
                genreIds.add(jg.getId());
            }
            gender1.setGenre_ids(genreIds);
            genreRepository.save(gender1);

            for (String q :
                    queriesTest) {
                JsonSearchPage searchPage = restTemplate.getForObject(uri + q, JsonSearchPage.class);
                searchPage.getResults().get(0).setBackdrop_path("https://image.tmdb.org/t/p/w500" + searchPage.getResults().get(0).getBackdrop_path());
                searchPage.getResults().get(0).setPoster_path("https://image.tmdb.org/t/p/w500" + searchPage.getResults().get(0).getPoster_path());
                Movie movie = new Movie(searchPage.getResults().get(0));
                searchRepository.save(movie);
            }
        }
    }
}