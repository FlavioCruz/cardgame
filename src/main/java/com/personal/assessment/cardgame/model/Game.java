package com.personal.assessment.cardgame.model;

import com.personal.assessment.cardgame.model.enums.GameStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    private Integer score;
    private Integer attemptsLeft;
    private LocalDateTime startTime = LocalDateTime.now();

    public Game() {
    }

    public Game(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.status = builder.status;
        this.score = builder.score;
        this.attemptsLeft = builder.attemptsLeft;
        this.startTime = builder.startTime;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Game setStatus(GameStatus status) {
        this.status = status;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Game setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public Game setAttemptsLeft(Integer attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Game setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public static class Builder {
        private User user;
        private Long id;
        private GameStatus status;
        private Integer score;
        private Integer attemptsLeft;
        private LocalDateTime startTime;

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStatus(GameStatus status) {
            this.status = status;
            return this;
        }

        public Builder setScore(Integer score) {
            this.score = score;
            return this;
        }

        public Builder setAttemptsLeft(Integer attemptsLeft) {
            this.attemptsLeft = attemptsLeft;
            return this;
        }

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
