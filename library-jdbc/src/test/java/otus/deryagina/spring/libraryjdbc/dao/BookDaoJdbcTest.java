package otus.deryagina.spring.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import otus.deryagina.spring.libraryjdbc.domain.*;
import otus.deryagina.spring.libraryjdbc.relation.BookAuthorRelation;
import otus.deryagina.spring.libraryjdbc.relation.BookGenreRelation;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao for work with books")
@Import({BookDaoJdbc.class, GenreDaoJdbc.class,AuthorDaoJdbc.class})
@JdbcTest
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 4;
    private static final String EXPECTED_BOOK_TITLE = "My book";
    private static final long GIVEN_ID = 1L;
    private static final long INCORRECT_GIVEN_ID = 500L;
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
    private static final long GIVEN_AUTHOR_ID_TO_ADD = 2L;
    private static final long GIVEN_AUTHOR_ID_TO_ADD2 = 3L;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @DisplayName("should return correct book count in database ")
    @Test
    void shouldReturnCorrectBookCount() {
        assertThat(bookDaoJdbc.findAll().size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("should  return correct book with correct given id")
    @Test
    void shouldReturnCorrectBookById() {
        assertThat(bookDaoJdbc.findById(GIVEN_ID)).hasFieldOrPropertyWithValue("title", EXPECTED_BOOK_TITLE);
    }

    @DisplayName("should return null with incorrect id")
    @Test
    void shouldReturnNullWithIncorrectId() {
        assertThat(bookDaoJdbc.findById(INCORRECT_GIVEN_ID)).isEqualTo(null);
    }

    @DisplayName("should return expected number of books with given title")
    @Test
    void shouldExpectedNumberOfBooksWithGivenTitle() {
        assertThat(bookDaoJdbc.findBooksByTitle(GIVEN_TITLE).size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE);
    }

    @DisplayName("should return all books with given title")
    @Test
    void shouldReturnAllBooksWithGivenTitle() {
        assertThat(bookDaoJdbc.findBooksByTitle(GIVEN_TITLE).stream().map(Book::getTitle).collect(Collectors.toList()))
                .containsOnly(GIVEN_TITLE);
    }

    @DisplayName("should return empty array if there is no books with given title")
    @Test
    void shouldReturnEmptyArrayIfNoBookWithGivenTitle() {
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
    void shouldUpdateTitleById() {
        bookDaoJdbc.updateBookTitle(GIVEN_ID, NEW_GIVEN_TITLE);
        Book actual = bookDaoJdbc.findById(GIVEN_ID);
        assertThat(actual.getTitle()).isEqualTo(NEW_GIVEN_TITLE);
    }

    @DisplayName("should add author to Book by authorId")
    @Test
    void shouldAddAuthorToBookByAuthorId() {
        bookDaoJdbc.addAuthorForBook(GIVEN_BOOK_ID, GIVEN_AUTHOR_ID_TO_ADD);
        Book actual = bookDaoJdbc.findById(GIVEN_BOOK_ID);
        System.out.println(actual);
        assertThat(actual.getAuthors().stream().map(Author::getId)).contains(GIVEN_AUTHOR_ID_TO_ADD);

    }

    @DisplayName("should Correctly Delete book by existing  BookId")
    @Test
    void shouldCorrectlyDeleteBookByExistingBookId() {
        bookDaoJdbc.deleteBookById(GIVEN_ID);
        assertThat(bookDaoJdbc.findById(GIVEN_ID)).isNull();
        Map<String, Object> map = new HashMap<>(1);
        map.put("bookId", GIVEN_ID);
        assertThat(namedParameterJdbcOperations.query("select bookId, genreId from BOOKS_GENRES_CORRELATION sc where bookId =:bookId order by BOOKID, GENREID", map,
                (rs, i) -> new BookGenreRelation(rs.getLong(1), rs.getLong(2)))).isEmpty();
        assertThat(namedParameterJdbcOperations.query("select bookId, AUTHORID from BOOKS_AUTHORS_CORRELATION sc where bookId =:bookId order by BOOKID, AUTHORID", map,
                (rs, i) -> new BookAuthorRelation(rs.getLong(1), rs.getLong(2)))).isEmpty();
    }

    @DisplayName("should return null book if delete all authors from book")
    @Test
    void shouldCorrectlyDeleteAuthorsFromBook() {
        bookDaoJdbc.deleteAllAuthorsFromBook(GIVEN_ID);
        Book book = bookDaoJdbc.findById(GIVEN_ID);
        assertThat(book).isNull();
    }

    @DisplayName("should correctly delete all genres from book")
    @Test
    void shouldCorrectlyDeleteGenresFromBook() {
        bookDaoJdbc.deleteAllGenresFromBook(GIVEN_ID);
        Book book = bookDaoJdbc.findById(GIVEN_ID);
        assertThat(book).isNull();
    }

    @DisplayName("should add authors to Book by authorsId")
    @Test
    void shouldAddAuthorsToBookByAuthorIds() {
        List<Long> authorsIds = new ArrayList<>();
        authorsIds.add(GIVEN_AUTHOR_ID_TO_ADD);
        authorsIds.add(GIVEN_AUTHOR_ID_TO_ADD2);
        bookDaoJdbc.addNewAuthorsToBook(GIVEN_BOOK_ID, authorsIds);
        Book actual = bookDaoJdbc.findById(GIVEN_BOOK_ID);
        System.out.println(actual);
        assertThat(actual.getAuthors().stream().map(Author::getId)).contains(GIVEN_AUTHOR_ID_TO_ADD);
        assertThat(actual.getAuthors().stream().map(Author::getId)).contains(GIVEN_AUTHOR_ID_TO_ADD2);

    }
}