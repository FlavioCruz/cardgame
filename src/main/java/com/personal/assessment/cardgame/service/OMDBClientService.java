package com.personal.assessment.cardgame.service;

import com.personal.assessment.cardgame.model.Movie;
import com.personal.assessment.cardgame.model.OMDBResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OMDBClientService {
    @Value("${omdb.api-key}")
    private String apiKey;
    private final String apiUrl = "http://www.omdbapi.com/";

    private final RestTemplate restTemplate;

    public OMDBClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie fetchMovie(String title) {
        String url = String.format("%s?t=%s&apikey=%s", apiUrl, title.replace(" ", "+"), apiKey);
        OMDBResponse response = restTemplate.getForObject(url, OMDBResponse.class);
        if (response != null && response.isValid()) {
            return new Movie.Builder()
                    .setId(null)
                    .setTitle(response.getTitle())
                    .setRating(response.getImdbRating())
                    .setVotes(response.getImdbVotes())
                    .build();
        }
        return null;
    }
}
