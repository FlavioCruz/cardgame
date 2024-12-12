package com.personal.assessment.cardgame.dto;

public class UserRankingDto {

    private String username;
    private Integer score;

    public UserRankingDto(Builder builder) {
        this.username = builder.username;
        this.score = builder.score;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public static class Builder {
        private String username;
        private Integer score;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setScore(Integer score) {
            this.score = score;
            return this;
        }

        public UserRankingDto build() {
            return new UserRankingDto(this);
        }
    }
}
