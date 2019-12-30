package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with books")
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
    private static final String NEW_BOOK_TITLE = "New book title";
    private static final long EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Poetry";
    private static final long EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "First author";
    private static final String NEW_GIVEN_TITLE = "Updated Title";
    private static final long GIVEN_BOOK_ID = 2L;
    private static final long GIVEN_AUTHOR_ID_TO_ADD = 2L ;

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

    @DisplayName("should add book to DB")
    @Test
    void shouldInsertBook() {
        Book book = new Book();
        book.setTitle(NEW_BOOK_TITLE);
        Genre genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        book.setGenres(Collections.singletonList(genre));
        Author author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        book.setAuthors(Collections.singletonList(author));
        long newId = bookDaoJdbc.insert(book);
        Book actual = bookDaoJdbc.findById(newId);
        assertThat(actual.getTitle()).isEqualTo(book.getTitle());
        assertThat(actual.getAuthors().get(0)).isEqualToComparingFieldByField(author);
        assertThat(actual.getGenres().get(0)).isEqualToComparingFieldByField(genre);
    }

    @DisplayName("should update title by id")
    @Test
    void shouldUpdateTitleById(){
        bookDaoJdbc.updateBookTitle(GIVEN_ID,NEW_GIVEN_TITLE);
        Book actual = bookDaoJdbc.findById(GIVEN_ID);
        assertThat(actual.getTitle()).isEqualTo(NEW_GIVEN_TITLE);
    }

    @DisplayName("should add author to Book by authorId")
    @Test
    void  shouldAddAuthorToBookByAuthorId(){
        bookDaoJdbc.addAuthorForBook(GIVEN_BOOK_ID,GIVEN_AUTHOR_ID_TO_ADD);
        Book actual = bookDaoJdbc.findById(GIVEN_BOOK_ID);
        System.out.println(actual);
        assertThat(actual.getAuthors().stream().map(Author::getId)).contains(GIVEN_AUTHOR_ID_TO_ADD);

    }
    @DisplayName("should Correctly Delete Authors From Existing BookId")
    @Test
    void shouldCorrectlyDeleteAuthorsFromExistingBookId(){
        bookDaoJdbc.deleteBookById(GIVEN_ID);
        assertThat(bookDaoJdbc.findById(GIVEN_ID)).isNull();

    }
}