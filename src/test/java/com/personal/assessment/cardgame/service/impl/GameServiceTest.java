package com.personal.assessment.cardgame.service.impl;

import com.personal.assessment.cardgame.dto.RoundDto;
import com.personal.assessment.cardgame.dto.UserRankingDto;
import com.personal.assessment.cardgame.exception.GameNotFoundException;
import com.personal.assessment.cardgame.exception.NoAttemptsLeftException;
import com.personal.assessment.cardgame.model.Game;
import com.personal.assessment.cardgame.model.Movie;
import com.personal.assessment.cardgame.model.Round;
import com.personal.assessment.cardgame.model.User;
import com.personal.assessment.cardgame.model.enums.GameStatus;
import com.personal.assessment.cardgame.repository.GameRepository;
import com.personal.assessment.cardgame.repository.MovieRepository;
import com.personal.assessment.cardgame.repository.RoundRepository;
import com.personal.assessment.cardgame.repository.UserRepository;
import com.personal.assessment.cardgame.service.MoviePairCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private RoundRepository roundRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MoviePairCache moviePairCache;
    @InjectMocks
    private GameServiceImpl gameService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void startGame_ShouldReturnRoundDto() {
        Long userId = 1L;

        User user = new User.Builder()
                .setId(userId)
                .setUsername("testuser")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Game game = new Game.Builder()
                .setId(1L)
                .setUser(user)
                .setStatus(GameStatus.ACTIVE)
                .build();
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Movie movie1 = generateMovieEntity(1L, "Movie 1", 8.0, 1000L);
        Movie movie2 = generateMovieEntity(2L, "Movie 2", 7.5, 800L);
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));
        when(moviePairCache.isPairUsed(1L, movie1.getId(), movie2.getId())).thenReturn(false);

        Round round = new Round.Builder()
                .setId(1L)
                .setGame(game)
                .setMovie1(movie1)
                .setMovie2(movie2)
                .build();
        when(roundRepository.save(any(Round.class))).thenReturn(round);

        RoundDto result = gameService.startGame(userId);

        assertNotNull(result);
        assertNotNull(result.getMovie1Id());
        assertNotNull(result.getMovie2Id());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(roundRepository, times(1)).save(any(Round.class));
    }

    @Test
    void createRound_ShouldReturnNewRoundDto() {
        Long gameId = 1L;
        Game game = new Game.Builder()
                .setId(gameId)
                .setStatus(GameStatus.ACTIVE)
                .build();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Movie movie1 = generateMovieEntity(1L, "Movie 1", 8.0, 1000L);
        Movie movie2 = generateMovieEntity(2L, "Movie 2", 7.5, 800L);
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));
        when(moviePairCache.isPairUsed(gameId, movie1.getId(), movie2.getId())).thenReturn(false);

        Round round = new Round.Builder()
                .setId(1L)
                .setGame(game)
                .setMovie1(movie1)
                .setMovie2(movie2)
                .build();
        when(roundRepository.save(any(Round.class))).thenReturn(round);

        RoundDto result = gameService.createRound(gameId);

        assertNotNull(result);
        assertNotNull(result.getMovie1Id());
        assertNotNull(result.getMovie2Id());
        assertNotEquals(result.getMovie1Id(), result.getMovie2Id());
        verify(roundRepository, times(1)).save(any(Round.class));
    }

    @Test
    void createRound_ShouldNotFindGame() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
                GameNotFoundException.class,
                () -> gameService.createRound(1L)
        );
    }

    @Test
    void endGame_WithError() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
                GameNotFoundException.class,
                () -> gameService.createRound(1L)
        );
    }

    @Test
    void endGame_WithSuccess() {
        Game game = new Game.Builder()
                .setId(1L).build();
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        gameService.endGame(game.getId());
        assertEquals(GameStatus.ENDED, game.getStatus());
        verify(gameRepository).findById(game.getId());
    }

    @Test
    void getRanking_WithSuccess() {
        User user1 = new User.Builder().setId(1L).build();
        User user2 = new User.Builder().setId(2L).build();
        User user3 = new User.Builder().setId(3L).build();

        Game game1 = new Game.Builder().setId(1L).setScore(1).setUser(user1).build();
        Game game2 = new Game.Builder().setId(2L).setScore(2).setUser(user1).build();
        Game game3 = new Game.Builder().setId(3L).setScore(3).setUser(user1).build();

        when(gameRepository.findByUser(user1)).thenReturn(Optional.of(List.of(game1)));
        when(gameRepository.findByUser(user2)).thenReturn(Optional.of(List.of(game2)));
        when(gameRepository.findByUser(user3)).thenReturn(Optional.of(List.of(game3)));

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));

        List<UserRankingDto> ranking = gameService.getRanking();

        assertEquals(3, ranking.get(0).getScore());
        assertEquals(2, ranking.get(1).getScore());
        assertEquals(1, ranking.get(2).getScore());
    }

    @Test
    void submitAnswer_ShouldReturnCorrectResult() {
        Long id = 1L;
        Game game = new Game.Builder()
                .setId(id)
                .setAttemptsLeft(3)
                .setScore(0)
                .build();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        Movie movie1 = generateMovieEntity(1L, "Movie 1", 8.0, 1000L);
        Movie movie2 = generateMovieEntity(2L, "Movie 2", 7.5, 800L);
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        Round round = new Round.Builder()
                .setId(id)
                .setMovie1(movie1)
                .setMovie2(movie2)
                .setGame(game)
                .build();
        when(roundRepository.findById(id)).thenReturn(Optional.of(round));
        when(moviePairCache.isPairUsed(id, movie1.getId(), movie2.getId())).thenReturn(false);

        boolean isCorrect = gameService.submitAnswer(id, movie1.getId());

        assertTrue(isCorrect);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void submitAnswer_WithNoAttemptsLeft() {
        Long id = 1L;
        Game game = new Game.Builder()
                .setId(id)
                .setAttemptsLeft(0)
                .setScore(0)
                .build();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        Round round = new Round.Builder()
                .setId(id)
                .setMovie1(null)
                .setMovie2(null)
                .setGame(game)
                .build();
        when(roundRepository.findById(id)).thenReturn(Optional.of(round));


        assertThrows(
                NoAttemptsLeftException.class,
                () -> gameService.submitAnswer(id, 1L)
        );
    }

    private Movie generateMovieEntity(Long id, String title, Double rating, Long votes) {
        return new Movie.Builder()
                .setId(id)
                .setTitle(title)
                .setRating(rating)
                .setVotes(votes)
                .build();
    }
}