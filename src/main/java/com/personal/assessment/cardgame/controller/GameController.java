package com.personal.assessment.cardgame.controller;

import com.personal.assessment.cardgame.dto.RoundDto;
import com.personal.assessment.cardgame.exception.GameNotFoundException;
import com.personal.assessment.cardgame.exception.NoAttemptsLeftException;
import com.personal.assessment.cardgame.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;


    public GameController(GameService gameService) {
        this.gameService = gameService;

    }

    @GetMapping("/start")
    public ResponseEntity<RoundDto> startGame(@RequestParam Long userId) {
        RoundDto game = gameService.startGame(userId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/next-pair")
    public ResponseEntity<RoundDto> getNextMoviePair(@RequestParam Long gameId) {
        RoundDto round = gameService.createRound(gameId);
        return ResponseEntity.ok(round);
    }

    @GetMapping("/answer")
    public ResponseEntity<String> answer(@RequestParam Long roundId,
                                         @RequestParam Long selectedMovieId) {
        try {
            boolean correct = gameService.submitAnswer(roundId, selectedMovieId);
            return ResponseEntity.ok(correct ? "Correct!" : "Wrong!");
        } catch (NoAttemptsLeftException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/end")
    public ResponseEntity<String> endGame(@RequestParam Long gameId) {
        gameService.endGame(gameId);
        return ResponseEntity.ok("Game ended!");
    }
    
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking() {
        return ResponseEntity.ok(gameService.getRanking());
    }
}
