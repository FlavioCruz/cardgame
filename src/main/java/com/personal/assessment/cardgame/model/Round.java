package com.personal.assessment.cardgame.model;

import jakarta.persistence.*;

@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @ManyToOne
    @JoinColumn(name = "movie1_id", nullable = false)
    private Movie movie1;
    @ManyToOne
    @JoinColumn(name = "movie2_id", nullable = false)
    private Movie movie2;
    @Column
    private Boolean answered;
    private Boolean correct;

    public Round(Builder builder) {
        this.id = builder.id;
        this.game = builder.game;
        this.movie1 = builder.movie1;
        this.movie2 = builder.movie2;
        this.answered = builder.answered;
        this.correct = builder.correct;
    }

    public Round() {
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Movie getMovie1() {
        return movie1;
    }

    public Movie getMovie2() {
        return movie2;
    }

    public Boolean isAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public static class Builder {
        private Long id;
        private Game game;
        private Movie movie1;
        private Movie movie2;
        private boolean answered;
        private Boolean correct;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setGame(Game game) {
            this.game = game;
            return this;
        }

        public Builder setMovie1(Movie movie1) {
            this.movie1 = movie1;
            return this;
        }

        public Builder setMovie2(Movie movie2) {
            this.movie2 = movie2;
            return this;
        }

        public Builder setAnswered(boolean answered) {
            this.answered = answered;
            return this;
        }

        public Builder setCorrect(Boolean correct) {
            this.correct = correct;
            return this;
        }

        public Round build() {
            return new Round(this);
        }
    }
}
