package com.personal.assessment.cardgame.repository;

import com.personal.assessment.cardgame.model.Game;
import com.personal.assessment.cardgame.model.User;
import com.personal.assessment.cardgame.model.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<List<Game>> findByStatus(GameStatus status);

    Optional<List<Game>> findByUser(User user);
}
