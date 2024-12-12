package com.personal.assessment.cardgame.model;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Transient
    private Integer score;

    public User() {
    }

    public User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.score = builder.score;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getScore() {
        return score;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private Integer score;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setScore(Integer score) {
            this.score = score;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
