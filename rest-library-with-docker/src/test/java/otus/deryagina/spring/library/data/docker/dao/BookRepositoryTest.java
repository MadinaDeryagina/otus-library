package otus.deryagina.spring.library.data.docker.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import otus.deryagina.spring.library.data.docker.dao.BookRepository;
import otus.deryagina.spring.library.data.docker.domain.Author;
import otus.deryagina.spring.library.data.docker.domain.Book;
import otus.deryagina.spring.library.data.docker.domain.Genre;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository")
@Slf4j
@DataJpaTest
@EntityScan("otus.deryagina.spring.library.data.docker.domain")
@ComponentScan(basePackageClasses = BookRepository.class)
class BookRepositoryTest {

    private static final int EXPECTED_BOOK_COUNT = 4;
    private static final String EXPECTED_BOOK_TITLE="My book";
    private static final long GIVEN_ID=1L;
    private static final long INCORRECT_GIVEN_ID=500L;
    private static final String GIVEN_TITLE = "Same title dif authors";
    private static final int EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE = 2;
    private static final String NO_BOOK_TITLE = "No book with this title";
    private static final String NEW_BOOK_TITLE = "New book title";
    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Poetry";
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "First author";
    private static final String NEW_GIVEN_TITLE = "Updated Title";


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("should return correct book count in database ")
    @Test
    void shouldReturnCorrectBookCount() {
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("should  return correct book with correct given id")
    @Test
    void shouldReturnCorrectBookById(){
        assertThat(bookRepository.findById(GIVEN_ID).get()).hasFieldOrPropertyWithValue("title",EXPECTED_BOOK_TITLE);
    }


    @DisplayName("should return empty optional with incorrect id")
    @Test
    void shouldReturnNullWithIncorrectId(){
        assertThat(bookRepository.findById(INCORRECT_GIVEN_ID)).isNotPresent();
    }


    @DisplayName("should return expected number of books with given title")
    @Test
    void shouldExpectedNumberOfBooksWithGivenTitle(){
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE);
    }


    @DisplayName("should return all books with given title")
    @Test
    void shouldReturnAllBooksWithGivenTitle(){
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).stream().map(Book::getTitle).collect(Collectors.toList()))
                .containsOnly(GIVEN_TITLE);
    }

    @DisplayName("should return empty array if there is no books with given title")
    @Test
    void shouldReturnEmptyArrayIfNoBookWithGivenTitle(){
        assertThat(bookRepository.findAllByTitle(NO_BOOK_TITLE)).isEmpty();
    }

    @DisplayName("should add book to DB")
    @Test
    void shouldInsertBook() {
        Book book = new Book();
        book.setTitle(NEW_BOOK_TITLE);
        Genre genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        book.setGenres(Collections.singletonList(genre));
        Author author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        book.setAuthors(Collections.singletonList(author));
        long newId = bookRepository.save(book).getId();
        Book actual = entityManager.find(Book.class, newId);
        assertThat(actual.getTitle()).isEqualTo(book.getTitle());
        assertThat(actual.getAuthors().get(0)).isEqualToComparingFieldByField(author);
        assertThat(actual.getGenres().get(0)).isEqualToComparingFieldByField(genre);
    }

    @DisplayName("should update title by id")
    @Test
    void shouldUpdateTitleById(){
        Book bookToUpdate = entityManager.find(Book.class,GIVEN_ID);
        System.out.println(bookToUpdate);
        bookToUpdate.setTitle(NEW_GIVEN_TITLE);
        bookRepository.save(bookToUpdate);
        Book actual = bookRepository.findById(GIVEN_ID).get();
        assertThat(actual.getTitle()).isEqualTo(NEW_GIVEN_TITLE);
        System.out.println(actual);
    }
}