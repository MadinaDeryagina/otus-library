package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with books")
@ExtendWith(SpringExtension.class)
@Import(BookDaoJdbc.class)
@JdbcTest
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 2;
    private static final String EXPECTED_BOOK_TITLE="My book";
    private static final long GIVEN_ID=1L;
    private static final long INCORRECT_GIVEN_ID=500L;

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


}