package com.personal.assessment.cardgame.model;

import jakarta.persistence.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Double rating;
    @Column(nullable = false)
    private Long votes;

    public Movie() {
    }

    public Movie(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.rating = builder.rating;
        this.votes = builder.votes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public Long getVotes() {
        return votes;
    }

    public static class Builder {
        private Long id;
        private String title;
        private Double rating;
        private Long votes;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setRating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setVotes(Long votes) {
            this.votes = votes;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
