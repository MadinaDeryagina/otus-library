package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with genres")
@ExtendWith(SpringExtension.class)
@Import(GenreDaoJdbc.class)
@JdbcTest
class GenreDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 5;
    private static final String INVALID_NAME = "BLA_BLA";
    private static List<String> namesOfGenres;
    private static final String GIVEN_GENRE_NAME = "Drama";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;


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
    @DisplayName("should return null by invalid name")
    void findGenreByInvalidName() {
        assertThat(genreDaoJdbc.findGenreByName(INVALID_NAME)).isNull();
    }
}