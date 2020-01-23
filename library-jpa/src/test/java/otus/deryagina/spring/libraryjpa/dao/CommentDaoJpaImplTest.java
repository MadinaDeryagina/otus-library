package otus.deryagina.spring.libraryjpa.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import otus.deryagina.spring.libraryjpa.domain.Book;
import otus.deryagina.spring.libraryjpa.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentDao")
@Import(CommentDaoJpaImpl.class)
@DataJpaTest
@Slf4j
class CommentDaoJpaImplTest {

    private static final long EXPECTED_BOOK_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    @DisplayName("should delete all comments from book, but book should exist")
    void shouldDeleteAllCommentsFromBookButBookShouldStay() {
        commentDao.deleteAllCommentsFromBook(EXPECTED_BOOK_ID);
        assertThat(commentDao.findAllCommentsToBook(EXPECTED_BOOK_ID)).isEmpty();
        assertThat(entityManager.find(Book.class, EXPECTED_BOOK_ID)).isNotNull();
    }

    @DisplayName("should find all comments to book")
    @Test
    void showAllCommentsToBook() {
        List<Comment> comments = commentDao.findAllCommentsToBook(EXPECTED_BOOK_ID);
        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(EXPECTED_NUMBER_OF_COMMENTS);
        System.out.println("comments:" + comments);
    }
}