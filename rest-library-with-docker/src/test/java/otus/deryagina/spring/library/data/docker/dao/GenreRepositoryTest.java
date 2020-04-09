package otus.deryagina.spring.library.data.docker.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import otus.deryagina.spring.library.data.docker.dao.GenreRepository;
import otus.deryagina.spring.library.data.docker.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreRepository")
@Slf4j
@DataJpaTest
@EntityScan("otus.deryagina.spring.library.data.docker.domain")
@ComponentScan(basePackageClasses = GenreRepository.class)
class GenreRepositoryTest {

    private static final String INVALID_NAME = "BLA_BLA";
    private static List<String> namesOfGenres;
    private static final String GIVEN_GENRE_NAME = "Drama";

    @Autowired
    private GenreRepository genreRepository;


    @BeforeAll
    static void init() {
        namesOfGenres = new ArrayList<>();
        namesOfGenres.add("Poetry");
        namesOfGenres.add("Drama");
    }


    @Test
    @DisplayName("should find correct genres by names")
    void findGenresByNames() {
        List<Genre> genresByNames = genreRepository.findAllByNameIn(namesOfGenres);
        List<String> namesFromDB = genresByNames.stream()
                .map(Genre::getName).collect(Collectors.toList());
        assertThat(namesFromDB).containsSequence(namesOfGenres);
    }

    @Test
    @DisplayName(("should return correct genre by correct name"))
    void findGenreByCorrectName() {
        Optional<Genre> genre = genreRepository.findByName(GIVEN_GENRE_NAME);
        assertThat(genre).isPresent();
        assertThat(genre.get().getName()).isEqualTo(GIVEN_GENRE_NAME);
    }

    @Test
    @DisplayName(("should return empty genre list by non existing names"))
    void shouldReturnEmptyListForNamesWhichAreNotInDB() {
        List<String> namesWhichAreNotInDB = new ArrayList<>();
        namesWhichAreNotInDB.add("Invalid genre");
        Assertions.assertThat(genreRepository.findAllByNameIn(namesWhichAreNotInDB)).isEmpty();

    }

    @Test
    @DisplayName("should return empty optional by invalid name")
    void findGenreByInvalidName() {
        Assertions.assertThat(genreRepository.findByName(INVALID_NAME)).isNotPresent();
    }

}