package otus.deryagina.spring.reactive.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import otus.deryagina.spring.reactive.library.domain.Author;
import otus.deryagina.spring.reactive.library.domain.Book;
import otus.deryagina.spring.reactive.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RouterFunctionConfigTest {

    @Autowired
    private RouterFunction route;

    private Genre drama;
    private Genre prose;
    private Genre fantasy;
    private Author shakespeare;
    private Author almostShakespeare;
    private Author tolkien;
    private Book hamlet;
    private Book lordOfRings;
    private Book almostHamlet;

    private WebTestClient client;

    @BeforeEach
    void init() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
        drama = new Genre("1","Drama");
        prose = new Genre("2","Prose");
        fantasy = new Genre("3","Fantasy");
        shakespeare = new Author("1","William Shakespeare");
        tolkien = new Author("2","J. R. R. Tolkien");
        almostShakespeare = new Author("3","William");
        List<Author> authorsOfHamlet = new ArrayList<>();
        authorsOfHamlet.add(shakespeare);
        List<Genre> genresOfHamlet = new ArrayList<>();
        genresOfHamlet.add(drama);
        genresOfHamlet.add(prose);
        hamlet = new Book("1","Hamlet", authorsOfHamlet, genresOfHamlet);

        List<Author> authorsOfLordOdRings = new ArrayList<>();
        authorsOfLordOdRings.add(tolkien);
        List<Genre> genreOfLordOfRings = new ArrayList<>();
        genreOfLordOfRings.add(fantasy);
        lordOfRings = new Book("2","The Lord of the Rings", authorsOfLordOdRings, genreOfLordOfRings);

        List<Author> authorsOfAlmostHamlet = new ArrayList<>();
        authorsOfAlmostHamlet.add(almostShakespeare);
        almostHamlet = new Book("3","Hamlet", authorsOfAlmostHamlet, genresOfHamlet);

    }

    @DisplayName("Get /books should return 200 and all expected books")
    @Test
    void shouldGetAllExpectedBooks() {
        client.get()
                .uri("/func/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .contains(hamlet,almostHamlet,lordOfRings);
    }

    @DisplayName("should throw exception if try to delete author with books")
    @Test
    void shouldThrowExceptionWhenDeleteAuthorWithBooks(){
        client.delete()
                .uri("/func/authors/1")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
    @DisplayName("should correctly delete author with no books")
    @Test
    void shouldCorrectlyDeleteAuthorWithNoBooks(){
        client.delete()
                .uri("/func/authors/4")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}