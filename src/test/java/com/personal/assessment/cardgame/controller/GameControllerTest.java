package com.personal.assessment.cardgame.controller;


import com.personal.assessment.cardgame.model.Game;
import com.personal.assessment.cardgame.model.Movie;
import com.personal.assessment.cardgame.model.User;
import com.personal.assessment.cardgame.model.enums.GameStatus;
import com.personal.assessment.cardgame.repository.GameRepository;
import com.personal.assessment.cardgame.repository.MovieRepository;
import com.personal.assessment.cardgame.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        gameRepository.deleteAll();
        movieRepository.deleteAll();
        user = userRepository.save(
                new User.Builder()
                        .setUsername("user")
                        .setPassword("a")
                        .build()
        );

        Movie movie1 = new Movie.Builder()
                .setTitle("Movie 1")
                .setRating(8.5)
                .setVotes(1000L)
                .build();

        Movie movie2 = new Movie.Builder()
                .setTitle("Movie 2")
                .setRating(7.0)
                .setVotes(800L)
                .build();

        movieRepository.saveAll(List.of(movie1, movie2));
    }

    @Test
    @Disabled
    @WithMockUser(username = "testUser", roles = {"USER"})
    void startGame_ShouldReturnGame() throws Exception {
        mockMvc.perform(get("/game/start")
                        .param("userId", user.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void generateMoviePair_ShouldReturnPair() throws Exception {
        Game game = gameRepository.save(
                new Game.Builder()
                        .setUser(user)
                        .setStatus(GameStatus.ACTIVE)
                        .setAttemptsLeft(3)
                        .build()
        );
        mockMvc.perform(get("/game/next-pair")
                        .param("gameId", game.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
