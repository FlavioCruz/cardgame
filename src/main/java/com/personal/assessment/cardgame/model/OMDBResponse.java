package com.personal.assessment.cardgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OMDBResponse {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("imdbVotes")
    private String imdbVotes;

    public boolean isValid() {
        return title != null && imdbRating != null && imdbVotes != null;
    }

    public Double getImdbRating() {
        try {
            return Double.parseDouble(imdbRating);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Long getImdbVotes() {
        try {
            return Long.parseLong(imdbVotes.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public String getTitle() {
        return title;
    }

    public OMDBResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public OMDBResponse setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
        return this;
    }

    public OMDBResponse setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
        return this;
    }
}
