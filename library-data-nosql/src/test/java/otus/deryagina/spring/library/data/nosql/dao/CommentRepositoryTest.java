package otus.deryagina.spring.library.data.nosql.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.deryagina.spring.library.data.nosql.domain.Author;
import otus.deryagina.spring.library.data.nosql.domain.Book;
import otus.deryagina.spring.library.data.nosql.domain.Comment;
import otus.deryagina.spring.library.data.nosql.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository")
@ComponentScan("otus.deryagina.spring.library.data.nosql.events")
class CommentRepositoryTest extends AbstractRepositoryTest {

    private static final String EXPECTED_BOOK_TITLE = "My book";
    private static final String EXISTING_GENRE_NAME = "Poetry";
    private static final String EXISTING_AUTHOR_NAME = "First author";

    @Autowired
    private CommentRepository commentRepository;

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

    @Test
    @DisplayName("should delete all comments from book, but book should exist and comment not exist")
    void shouldDeleteAllCommentsFromBookButBookShouldStay() {
        Book lordOfRings = bookRepository.findAllByTitle("The Lord of the Rings").get(0);
        String bookId = lordOfRings.getId();
        String commentId =   commentRepository.findAllByBook_Id(bookId).get(0).getId();
        commentRepository.deleteAllByBook_Id(bookId);
        assertThat(commentRepository.findAllByBook_Id(bookId)).isEmpty();
        Assertions.assertThat(mongoTemplate.findById(bookId, Book.class)).isNotNull();
        Assertions.assertThat(mongoTemplate.findById(commentId, Comment.class)).isNull();
    }

    @DisplayName("should delete comment when book is deleted")
    @Test
    void shouldDeleteCommentWhenBookIsDeleted() {
        myBook = mongoTemplate.save(myBook);
        Comment myComment = new Comment( myBook, "My Wow");
        myComment = mongoTemplate.save(myComment);
        String commentId =   commentRepository.findAllByBook_Id(myBook.getId()).get(0).getId();
        bookRepository.delete(myBook);
        Assertions.assertThat(mongoTemplate.findById(commentId, Comment.class)).isNull();
    }
}