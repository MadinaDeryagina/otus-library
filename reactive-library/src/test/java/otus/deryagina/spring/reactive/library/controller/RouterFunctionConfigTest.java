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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RouterFunctionConfigTest {

    private static final String EXPECTED_NEW_AUTHOR_NAME = "New Bill";
    private static final String EXPECTED_NEW_BOOK_TITLE = "New title";
    private static final String EXPECTED_NEW_GENRE_NAME = "Detective";
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
        drama = new Genre("1", "Drama");
        prose = new Genre("2", "Prose");
        fantasy = new Genre("3", "Fantasy");
        shakespeare = new Author("1", "William Shakespeare");
        tolkien = new Author("2", "J. R. R. Tolkien");
        almostShakespeare = new Author("3", "William");
        List<Author> authorsOfHamlet = new ArrayList<>();
        authorsOfHamlet.add(shakespeare);
        List<Genre> genresOfHamlet = new ArrayList<>();
        genresOfHamlet.add(drama);
        genresOfHamlet.add(prose);
        hamlet = new Book("1", "Hamlet", authorsOfHamlet, genresOfHamlet);

        List<Author> authorsOfLordOdRings = new ArrayList<>();
        authorsOfLordOdRings.add(tolkien);
        List<Genre> genreOfLordOfRings = new ArrayList<>();
        genreOfLordOfRings.add(fantasy);
        lordOfRings = new Book("2", "The Lord of the Rings", authorsOfLordOdRings, genreOfLordOfRings);

        List<Author> authorsOfAlmostHamlet = new ArrayList<>();
        authorsOfAlmostHamlet.add(almostShakespeare);
        almostHamlet = new Book("3", "Hamlet", authorsOfAlmostHamlet, genresOfHamlet);

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
                .contains(hamlet, almostHamlet, lordOfRings);
    }

    @DisplayName("should throw exception if try to delete author with books")
    @Test
    void shouldThrowExceptionWhenDeleteAuthorWithBooks() {
        client.delete()
                .uri("/func/authors/1")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @DisplayName("should correctly delete author with no books")
    @Test
    void shouldCorrectlyDeleteAuthorWithNoBooks() {
        client.delete()
                .uri("/func/authors/4")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @DisplayName("Post /books should correctly save book and related objects")
    @Test
    void shouldCorrectlySaveBookAndRelatedObjects() {
        Book newBook = new Book();
        newBook.setTitle(EXPECTED_NEW_BOOK_TITLE);
        //prepare authors for new book and set
        Author existingAuthor = shakespeare;
        Author newAuthor = new Author();
        newAuthor.setFullName(EXPECTED_NEW_AUTHOR_NAME);
        List<Author> authorsForNewBook = new ArrayList<>();
        authorsForNewBook.add(newAuthor);
        authorsForNewBook.add(existingAuthor);
        newBook.setAuthors(authorsForNewBook);
        //prepare genres for new book and set
        List<Genre> genresForNewBook = new ArrayList<>();
        Genre existingGenre = fantasy;
        Genre newGenre = new Genre();
        newGenre.setName(EXPECTED_NEW_GENRE_NAME);
        genresForNewBook.add(existingGenre);
        genresForNewBook.add(newGenre);
        newBook.setGenres(genresForNewBook);
        client.post()
                .uri("/func/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .consumeWith(bookEntityExchangeResult -> {
                    Book book = bookEntityExchangeResult.getResponseBody();
                    assertThat(book.getTitle()).isEqualTo(EXPECTED_NEW_BOOK_TITLE);
                    assertThat(book.getAuthors()).containsExactlyInAnyOrder(existingAuthor,newAuthor);
                    assertThat(book.getGenres()).containsExactlyInAnyOrder(existingGenre,newGenre);
                });

        // check that new author now in library too
        client.get()
                .uri("/func/authors?name=" + EXPECTED_NEW_AUTHOR_NAME)
                .exchange()
                .expectStatus().isOk()
                //.expectBody(Author.class)
                //.consumeWith(authorEntityExchangeResult -> System.out.println(authorEntityExchangeResult));
                .expectBody()
                .jsonPath("fullName").isEqualTo(EXPECTED_NEW_AUTHOR_NAME)
                .jsonPath("id").isNotEmpty();
        //check that new genre now in library too
        client.get()
                .uri("/func/genres?name=" + EXPECTED_NEW_GENRE_NAME)
                .exchange()
                .expectStatus().isOk()
                //.expectBody(Author.class)
                //.consumeWith(authorEntityExchangeResult -> System.out.println(authorEntityExchangeResult));
                .expectBody()
                .jsonPath("name").isEqualTo(EXPECTED_NEW_GENRE_NAME)
                .jsonPath("id").isNotEmpty();

    }

}