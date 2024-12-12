package com.personal.assessment.cardgame.exception;

public class NoAttemptsLeftException extends RuntimeException {

    private static final String MESSAGE = "No attempts left. Game over.";

    public NoAttemptsLeftException() {
        super(MESSAGE);
    }
}
