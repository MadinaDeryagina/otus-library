package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.libraryjdbc.domain.Book;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with books")
@ExtendWith(SpringExtension.class)
@Import({BookDaoJdbc.class,GenreDaoJdbc.class})
@JdbcTest
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 4;
    private static final String EXPECTED_BOOK_TITLE="My book";
    private static final long GIVEN_ID=1L;
    private static final long INCORRECT_GIVEN_ID=500L;
    private static final String GIVEN_TITLE = "Same title dif authors";
    private static final int EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE = 2;
    private static final String NO_BOOK_TITLE = "No book with this title";

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("should return correct book count in database ")
    @Test
    void shouldReturnCorrectBookCount() {
        assertThat(bookDaoJdbc.findAll().size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("should  return correct book with correct given id")
    @Test
    void shouldReturnCorrectBookById(){
        assertThat(bookDaoJdbc.findById(GIVEN_ID)).hasFieldOrPropertyWithValue("title",EXPECTED_BOOK_TITLE);
    }

    @DisplayName("should return null with incorrect id")
    @Test
    void shouldReturnNullWithIncorrectId(){
        assertThat(bookDaoJdbc.findById(INCORRECT_GIVEN_ID)).isEqualTo(null);
    }

    @DisplayName("should return expected number of books with given title")
    @Test
    void shouldExpectedNumberOfBooksWithGivenTitle(){
        assertThat(bookDaoJdbc.findBooksByTitle(GIVEN_TITLE).size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE);
    }

    @DisplayName("should return all books with given title")
    @Test
    void shouldReturnAllBooksWithGivenTitle(){
        assertThat(bookDaoJdbc.findBooksByTitle(GIVEN_TITLE).stream().map(Book::getTitle).collect(Collectors.toList()))
                .containsOnly(GIVEN_TITLE);
    }

    @DisplayName("should return empty array if there is no books with given title")
    @Test
    void shouldReturnEmptyArrayIfNoBookWithGivenTitle(){
        assertThat(bookDaoJdbc.findBooksByTitle(NO_BOOK_TITLE)).isEmpty();
    }



}