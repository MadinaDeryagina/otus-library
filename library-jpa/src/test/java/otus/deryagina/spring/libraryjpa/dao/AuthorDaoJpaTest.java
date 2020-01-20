package otus.deryagina.spring.libraryjpa.dao;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import otus.deryagina.spring.libraryjpa.domain.Author;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDao")
@Import(AuthorDaoJpaImpl.class)
@DataJpaTest
@Slf4j
class AuthorDaoJpaTest {

    private static final String NEW_AUTHOR_NAME ="New author" ;
    private static List<String> validAuthorsNames;

    @Autowired
    private AuthorDaoJpaImpl authorDaoJpa;

    @Autowired
    private TestEntityManager em;


    @BeforeAll
    static void init() {
        validAuthorsNames = new ArrayList<>();
        validAuthorsNames.add("First author");
        validAuthorsNames.add("Second author");
    }

    @Test
    @DisplayName(("should return correct authors by correct authors' names"))
    void shouldReturnCorrectAuthorsByCorrectAuthorsNames() {
        List<Author> authorsByNames = authorDaoJpa.findAuthorsByNames(validAuthorsNames);
        List<String> namesFromDB = authorsByNames.stream()
                .map(Author::getFullName).collect(Collectors.toList());
        assertThat(namesFromDB).containsSequence(validAuthorsNames);
    }

    @Test
    @DisplayName(("should return empty authors list by non existing names"))
    void shouldReturnEmptyListForNamesWhichAreNotInDB() {
        List<String> namesWhichAreNotInDB = new ArrayList<>();
        namesWhichAreNotInDB.add("Invalid author");
        assertThat(authorDaoJpa.findAuthorsByNames(namesWhichAreNotInDB)).isEmpty();
    }

    @DisplayName("should add author to DB")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author();
        author.setFullName(NEW_AUTHOR_NAME);
        Author newAuthor = authorDaoJpa.save(author);
        val actual = em.find(Author.class,newAuthor.getId());
        assertThat(actual).isNotNull();
        log.info(actual.toString());
        assertThat(actual.getFullName()).isEqualTo(author.getFullName());
    }
}