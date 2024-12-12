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
import com.personal.assessment.cardgame.service.GameService;
import com.personal.assessment.cardgame.service.MoviePairCache;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MoviePairCache moviePairCache;

    public GameServiceImpl(GameRepository gameRepository, RoundRepository roundRepository, MovieRepository movieRepository,
                           UserRepository userRepository, MoviePairCache moviePairCache) {
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.moviePairCache = moviePairCache;
    }

    @Override
    public RoundDto startGame(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Game game = new Game.Builder()
                .setUser(user)
                .setStatus(GameStatus.ACTIVE)
                .setAttemptsLeft(3)
                .setScore(0)
                .build();
        gameRepository.save(game);

        return createRound(game);
    }

    @Override
    public RoundDto createRound(Long gameId) {
        Game game = gameRepository
                .findById(gameId)
                .orElseThrow(GameNotFoundException::new);
        return createRound(game);
    }

    @Override
    public boolean submitAnswer(Long roundId, Long selectedMovieId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(GameNotFoundException::new);

        Game game = round.getGame();

        if (game.getAttemptsLeft() <= 0) {
            throw new NoAttemptsLeftException();
        }

        Movie movie1 = round.getMovie1();
        Movie movie2 = round.getMovie2();

        Movie winner = (movie1.getRating() * movie1.getVotes()) > (movie2.getRating() * movie2.getVotes())
                ? movie1 : movie2;

        boolean result;
        if (winner.getId().equals(selectedMovieId)) {
            game.setScore(game.getScore() + 1);
            round.setAnswered(true);
            result = true;
        } else {
            game.setAttemptsLeft(game.getAttemptsLeft() - 1);
            round.setCorrect(false);
            result = false;
        }
        gameRepository.save(game);
        roundRepository.save(round);
        return result;
    }

    @Override
    public void endGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(GameNotFoundException::new);
        game.setStatus(GameStatus.ENDED);
        gameRepository.save(game);
    }

    @Override
    public List<UserRankingDto> getRanking() {
        return userRepository.findAll()
                .stream()
                .map(
                        user -> new UserRankingDto.Builder()
                                .setUsername(user.getUsername())
                                .setScore(getScoreByUser(user))
                                .build()
                )
                .sorted(Comparator.comparing(UserRankingDto::getScore).reversed())
                .toList();
    }

    private RoundDto createRound(Game game) {
        List<Movie> moviePair = generateMoviePair(game.getId());
        Round round = new Round.Builder()
                .setGame(game)
                .setMovie1(moviePair.get(0))
                .setMovie2(moviePair.get(1))
                .build();
        roundRepository.save(round);
        return new RoundDto.Builder()
                .setGameId(round.getGame().getId())
                .setMovie1Id(round.getMovie1().getId())
                .setMovie1Title(round.getMovie1().getTitle())
                .setMovie2Id(round.getMovie2().getId())
                .setMovie2Title(round.getMovie2().getTitle())
                .setRoundId(round.getId())
                .setAnswered(false)
                .setCorrectMovieId(getCorrectMovieId(round))
                .build();
    }

    private List<Movie> generateMoviePair(Long gameId) {
        List<Movie> allMovies = movieRepository.findAll();
        Random random = new Random();

        Movie movie1, movie2;
        do {
            movie1 = allMovies.get(random.nextInt(allMovies.size()));
            movie2 = allMovies.get(random.nextInt(allMovies.size()));
        } while (movie1.equals(movie2) || moviePairCache.isPairUsed(gameId, movie1.getId(), movie2.getId()));

        moviePairCache.markPairAsUsed(gameId, movie1.getId(), movie2.getId());
        return List.of(movie1, movie2);
    }

    private Long getCorrectMovieId(Round round) {
        Movie movie1 = round.getMovie1();
        Movie movie2 = round.getMovie2();
        return (movie1.getRating() * movie1.getVotes()) > (movie2.getRating() * movie2.getVotes())
                ? movie1.getId()
                : movie2.getId();
    }

    private Integer getScoreByUser(User user) {
        List<Game> games = gameRepository.findByUser(user).orElseThrow(GameNotFoundException::new);
        Integer userScore = games.stream().map(Game::getScore).reduce(0, Integer::sum);
        return userScore;
    }
}
