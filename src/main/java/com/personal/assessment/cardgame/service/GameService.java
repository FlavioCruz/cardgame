package com.personal.assessment.cardgame.service;

import com.personal.assessment.cardgame.dto.RoundDto;
import com.personal.assessment.cardgame.dto.UserRankingDto;

import java.util.List;

public interface GameService {

    RoundDto startGame(Long userId);

    public RoundDto createRound(Long gameId);

    boolean submitAnswer(Long gameId, Long selectedMovieId);

    void endGame(Long gameId);

    List<UserRankingDto> getRanking();
}
