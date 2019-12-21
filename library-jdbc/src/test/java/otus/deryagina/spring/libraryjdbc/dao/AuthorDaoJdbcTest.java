package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.libraryjdbc.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with authors")
@ExtendWith(SpringExtension.class)
@Import(AuthorDaoJdbc.class)
@JdbcTest
class AuthorDaoJdbcTest {

    private static final String NEW_AUTHOR_NAME ="New author" ;
    private static List<String> validAuthorsNames;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;


    @BeforeAll
    static void init() {
        validAuthorsNames = new ArrayList<>();
        validAuthorsNames.add("First author");
        validAuthorsNames.add("Second author");
    }

    @Test
    @DisplayName(("should return correct authors by correct authors' names"))
    void shouldReturnCorrectAuthorsByCorrectAuthorsNames() {
        List<Author> authorsByNames = authorDaoJdbc.findAuthorsByNames(validAuthorsNames);
        List<String> namesFromDB = authorsByNames.stream()
                .map(Author::getFullName).collect(Collectors.toList());
        assertThat(namesFromDB).containsSequence(validAuthorsNames);
    }

    @Test
    @DisplayName(("should return empty authors list by non existing names"))
    void shouldReturnEmptyListForNamesWhichAreNotInDB() {
        List<String> namesWhichAreNotInDB = new ArrayList<>();
        namesWhichAreNotInDB.add("Invalid author");
        assertThat(authorDaoJdbc.findAuthorsByNames(namesWhichAreNotInDB)).isEmpty();
    }

    @DisplayName("should add author to DB")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author();
        author.setFullName(NEW_AUTHOR_NAME);
        long newId = authorDaoJdbc.insert(author);
        Author actual = authorDaoJdbc.findById(newId);
        assertThat(actual.getFullName()).isEqualTo(author.getFullName());
    }
}