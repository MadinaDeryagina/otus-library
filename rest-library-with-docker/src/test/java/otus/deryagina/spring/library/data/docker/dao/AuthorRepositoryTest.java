package otus.deryagina.spring.library.data.docker.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import otus.deryagina.spring.library.data.docker.dao.AuthorRepository;
import otus.deryagina.spring.library.data.docker.domain.Author;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepository")
@Slf4j
@DataJpaTest
@EntityScan("otus.deryagina.spring.library.data.docker.domain")
@ComponentScan(basePackageClasses = AuthorRepository.class)
class AuthorRepositoryTest {

    private static List<String> validAuthorsNames;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeAll
    static void init() {
        validAuthorsNames = new ArrayList<>();
        validAuthorsNames.add("First author");
        validAuthorsNames.add("Second author");
    }

    @Test
    @DisplayName(("should return correct authors by correct authors' names"))
    void shouldReturnCorrectAuthorsByCorrectAuthorsNames() {
        System.out.println(authorRepository);
        List<Author> authorsByNames = authorRepository.findAllByFullNameIn(validAuthorsNames);
        List<String> namesFromDB = authorsByNames.stream()
                .map(Author::getFullName).collect(Collectors.toList());
        assertThat(namesFromDB).containsSequence(validAuthorsNames);
    }

    @Test
    @DisplayName(("should return empty authors list by non existing names"))
    void shouldReturnEmptyListForNamesWhichAreNotInDB() {
        List<String> namesWhichAreNotInDB = new ArrayList<>();
        namesWhichAreNotInDB.add("Invalid author");
        assertThat(authorRepository.findAllByFullNameIn(namesWhichAreNotInDB)).isEmpty();
    }

}