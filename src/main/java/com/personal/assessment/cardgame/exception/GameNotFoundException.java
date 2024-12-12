package com.personal.assessment.cardgame.exception;

public class GameNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Game not found";

    public GameNotFoundException() {
        super(MESSAGE);
    }
}
