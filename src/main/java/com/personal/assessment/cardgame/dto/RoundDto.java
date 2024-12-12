package com.personal.assessment.cardgame.dto;

public class RoundDto {

    private Long gameId;
    private Long roundId;
    private Long movie1Id;
    private String movie1Title;
    private Long movie2Id;
    private String movie2Title;
    private boolean answered;
    private Long correctMovieId;

    public RoundDto(Builder builder) {
        gameId = builder.gameId;
        roundId = builder.roundId;
        movie1Id = builder.movie1Id;
        movie1Title = builder.movie1Title;
        movie2Id = builder.movie2Id;
        movie2Title = builder.movie2Title;
        answered = builder.answered;
        correctMovieId = builder.correctMovieId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getRoundId() {
        return roundId;
    }

    public Long getMovie1Id() {
        return movie1Id;
    }

    public String getMovie1Title() {
        return movie1Title;
    }

    public Long getMovie2Id() {
        return movie2Id;
    }

    public String getMovie2Title() {
        return movie2Title;
    }

    public boolean isAnswered() {
        return answered;
    }

    public Long getCorrectMovieId() {
        return correctMovieId;
    }

    public static class Builder {
        private Long gameId;
        private Long roundId;
        private Long movie1Id;
        private String movie1Title;
        private Long movie2Id;
        private String movie2Title;
        private boolean answered;
        private Long correctMovieId;

        public Builder setGameId(Long gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder setRoundId(Long roundId) {
            this.roundId = roundId;
            return this;
        }

        public Builder setMovie1Id(Long movie1Id) {
            this.movie1Id = movie1Id;
            return this;
        }

        public Builder setMovie1Title(String movie1Title) {
            this.movie1Title = movie1Title;
            return this;
        }

        public Builder setMovie2Id(Long movie2Id) {
            this.movie2Id = movie2Id;
            return this;
        }

        public Builder setMovie2Title(String movie2Title) {
            this.movie2Title = movie2Title;
            return this;
        }

        public Builder setAnswered(boolean answered) {
            this.answered = answered;
            return this;
        }

        public Builder setCorrectMovieId(Long correctMovieId) {
            this.correctMovieId = correctMovieId;
            return this;
        }

        public RoundDto build() {
            return new RoundDto(this);
        }
    }
}
