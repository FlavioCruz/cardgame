package com.personal.assessment.cardgame.repository;

import com.personal.assessment.cardgame.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByGameId(Long gameId);
}
