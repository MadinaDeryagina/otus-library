package otus.deryagina.spring.library.data.nosql.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.deryagina.spring.library.data.nosql.domain.Author;
import otus.deryagina.spring.library.data.nosql.domain.Book;
import otus.deryagina.spring.library.data.nosql.domain.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DisplayName("BookRepository")
class BookRepositoryTest extends AbstractRepositoryTest {

    private static final int EXPECTED_BOOK_COUNT = 3;
    private static final String EXPECTED_BOOK_TITLE = "My book";
    private static final String INCORRECT_GIVEN_ID = "500";
    private static final String GIVEN_TITLE = "Hamlet";
    private static final int EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE = 2;
    private static final String NO_BOOK_TITLE = "No book with this title";
    private static final String NEW_BOOK_TITLE = "New book title";
    private static final String EXISTING_GENRE_NAME = "Poetry";
    private static final String EXISTING_AUTHOR_NAME = "First author";
    private static final String NEW_GIVEN_TITLE = "Updated Title";


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Book myBook;

    @BeforeEach
    void init() {
        List<Author> myAuthors = new ArrayList<>();
        myAuthors.add(new Author(EXISTING_AUTHOR_NAME));
        List<Genre> myGenres = new ArrayList<>();
        myGenres.add(new Genre(EXISTING_GENRE_NAME));
        myBook = new Book(EXPECTED_BOOK_TITLE, myAuthors, myGenres);
    }

    @DisplayName("should return correct book count in database ")
    @Test
    void shouldReturnCorrectBookCount() {
        System.out.println(bookRepository.findAll());
        assertThat(bookRepository.findAll().size()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("should  return correct book with correct given id")
    @Test
    void shouldReturnCorrectBookById() {
        myBook = mongoTemplate.save(myBook);
        Assertions.assertThat(bookRepository.findById(myBook.getId()).get()).hasFieldOrPropertyWithValue("title", EXPECTED_BOOK_TITLE);
        bookRepository.delete(myBook);
    }


    @DisplayName("should return empty optional with incorrect id")
    @Test
    void shouldReturnNullWithIncorrectId() {
        Assertions.assertThat(bookRepository.findById(INCORRECT_GIVEN_ID)).isNotPresent();
    }


    @DisplayName("should return expected number of books with given title")
    @Test
    void shouldExpectedNumberOfBooksWithGivenTitle() {
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS_WITH_GIVEN_TITLE);
    }


    @DisplayName("should return all books with given title")
    @Test
    void shouldReturnAllBooksWithGivenTitle() {
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).stream().map(Book::getTitle).collect(Collectors.toList()))
                .containsOnly(GIVEN_TITLE);
    }

    @DisplayName("should return empty array if there is no books with given title")
    @Test
    void shouldReturnEmptyArrayIfNoBookWithGivenTitle() {
        Assertions.assertThat(bookRepository.findAllByTitle(NO_BOOK_TITLE)).isEmpty();
    }

    @DisplayName("should add book to DB")
    @Test
    void shouldInsertBook() {
        Book book = new Book();
        book.setTitle(NEW_BOOK_TITLE);
        Genre genre = new Genre(EXISTING_GENRE_NAME);
        book.setGenres(Collections.singletonList(genre));
        Author author = new Author(EXISTING_AUTHOR_NAME);
        book.setAuthors(Collections.singletonList(author));

        String newId = bookRepository.save(book).getId();
        Book actual = mongoTemplate.findById(newId,Book.class);
        assertThat(actual.getTitle()).isEqualTo(book.getTitle());
        assertThat(actual.getAuthors().get(0)).isEqualToComparingFieldByField(author);
        assertThat(actual.getGenres().get(0)).isEqualToComparingFieldByField(genre);
        bookRepository.delete(actual);
    }

    @DisplayName("should update title by id")
    @Test
    void shouldUpdateTitleById() {
        Book bookToUpdate = mongoTemplate.save(myBook);
        System.out.println(bookToUpdate);
        bookToUpdate.setTitle(NEW_GIVEN_TITLE);
        bookRepository.save(bookToUpdate);
        Book actual = mongoTemplate.findById(bookToUpdate.getId(), Book.class);
        if (actual != null) {
            assertThat(actual.getTitle()).isEqualTo(NEW_GIVEN_TITLE);
        }else {
            fail("incorrect update book title");
        }
        System.out.println(actual);
    }
}