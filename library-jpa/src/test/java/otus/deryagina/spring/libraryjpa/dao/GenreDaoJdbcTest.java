package otus.deryagina.spring.libraryjpa.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.deryagina.spring.libraryjpa.domain.Genre;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with genres")
@Import(GenreDaoJpaImpl.class)
@JdbcTest
class GenreDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 5;
    private static final String INVALID_NAME = "BLA_BLA";
    private static final String NEW_GENRE_NAME = "New genre";
    private static List<String> namesOfGenres;
    private static final String GIVEN_GENRE_NAME = "Drama";

    @Autowired
    private GenreDaoJpaImpl genreDaoJdbc;


    @BeforeAll
    static void init() {
        namesOfGenres = new ArrayList<>();
        namesOfGenres.add("Poetry");
        namesOfGenres.add("Drama");
    }


    @Test
    @DisplayName("should return expected number of genres")
    void shouldReturnExpectedNumberOfGenres() {
        assertThat(genreDaoJdbc.findAll().size()).isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @Test
    @DisplayName("should find correct genres by names")
    void findGenresByNames() {
        List<Genre> genresByNames = genreDaoJdbc.findGenresByNames(namesOfGenres);
        List<String> namesFromDB = genresByNames.stream()
                .map(Genre::getName).collect(Collectors.toList());
        assertThat(namesFromDB).containsSequence(namesOfGenres);
    }

    @Test
    @DisplayName(("should return correct genre by correct name"))
    void findGenreByCorrectName() {
        Genre genre = genreDaoJdbc.findGenreByName(GIVEN_GENRE_NAME);
        assertThat(genre.getName()).isEqualTo(GIVEN_GENRE_NAME);
    }

    @Test
    @DisplayName(("should return empty genre list by non existing names"))
    void shouldReturnEmptyListForNamesWhichAreNotInDB() {
        List<String> namesWhichAreNotInDB = new ArrayList<>();
        namesWhichAreNotInDB.add("Invalid genre");
        assertThat(genreDaoJdbc.findGenresByNames(namesWhichAreNotInDB)).isEmpty();

    }

    @Test
    @DisplayName("should return null by invalid name")
    void findGenreByInvalidName() {
        assertThat(genreDaoJdbc.findGenreByName(INVALID_NAME)).isNull();
    }

    @DisplayName("should add genre to DB")
    @Test
    void shouldInsertGenre() {
        Genre genre = new Genre();
        genre.setName(NEW_GENRE_NAME);
        long newId = genreDaoJdbc.insert(genre);
        genre.setId(newId);
        Genre actual = genreDaoJdbc.findById(newId);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }
}