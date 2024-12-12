package com.personal.assessment.cardgame.configuration;

import com.personal.assessment.cardgame.model.Movie;
import com.personal.assessment.cardgame.repository.MovieRepository;
import com.personal.assessment.cardgame.service.OMDBClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository, OMDBClientService omdbService) {
        return args -> {
            List<String> movieTitles = List.of(
                    "The Godfather", "The Shawshank Redemption", "Schindler's List", "Raging Bull", "Casablanca",
                    "Citizen Kane", "Gone with the Wind", "The Wizard of Oz", "One Flew Over the Cuckoo's Nest",
                    "Lawrence of Arabia", "Vertigo", "Pulp Fiction", "The Good, the Bad and the Ugly",
                    "Forrest Gump", "Inception", "The Dark Knight", "12 Angry Men", "Fight Club",
                    "Goodfellas", "Star Wars: Episode IV - A New Hope", "The Matrix", "Se7en", "The Silence of the Lambs",
                    "The Godfather Part II", "The Lord of the Rings: The Return of the King", "Interstellar",
                    "The Green Mile", "Saving Private Ryan", "Parasite", "Joker", "Avengers: Endgame", "Gladiator",
                    "Braveheart", "The Lion King", "Titanic", "The Departed", "Whiplash", "Django Unchained",
                    "The Prestige", "Back to the Future", "The Grand Budapest Hotel", "American Beauty",
                    "The Social Network", "Shutter Island", "Inglourious Basterds", "The Pianist",
                    "The Wolf of Wall Street", "No Country for Old Men", "Slumdog Millionaire", "The Truman Show",
                    "Black Swan", "La La Land", "Mad Max: Fury Road", "The Imitation Game", "The Avengers",
                    "Doctor Strange", "Thor: Ragnarok", "Iron Man", "Guardians of the Galaxy", "Spider-Man: Homecoming",
                    "Deadpool", "Logan", "The Batman", "Wonder Woman", "The Incredible Hulk", "Captain Marvel",
                    "X-Men: Days of Future Past", "Ant-Man", "Aquaman", "Venom", "Justice League",
                    "Frozen", "Toy Story", "Up", "Coco", "Zootopia", "Inside Out", "Moana", "Finding Nemo",
                    "The Incredibles", "Shrek", "Monsters, Inc.", "Ratatouille", "Wall-E", "Kung Fu Panda",
                    "How to Train Your Dragon", "The Lego Movie", "Big Hero 6", "Despicable Me",
                    "The Little Mermaid", "Beauty and the Beast", "Aladdin", "Mulan", "The Jungle Book",
                    "Cinderella", "Snow White and the Seven Dwarfs", "Sleeping Beauty", "Tangled", "Pocahontas"
            );

            movieTitles.forEach(title -> {
                Movie movie = omdbService.fetchMovie(title);
                if (movie != null) {
                    movieRepository.save(movie);
                }
            });
        };
    }
}
