package otus.deryagina.spring.library.data.nosql.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import otus.deryagina.spring.library.data.nosql.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepository")
class AuthorRepositoryTest extends AbstractRepositoryTest{

    private static List<String> validAuthorsNames;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeAll
    static void init() {
        validAuthorsNames = new ArrayList<>();
        validAuthorsNames.add("William Shakespeare");
        validAuthorsNames.add("J. R. R. Tolkien");
    }

    @Test
    @DisplayName(("should return correct authors by correct authors' names"))
    void shouldReturnCorrectAuthorsByCorrectAuthorsNames() {
        System.out.println(authorRepository);
        List<Author> authorsByNames = authorRepository.findAllByFullNameIn(validAuthorsNames);
        System.out.println(authorsByNames);
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