package otus.deryagina.spring.library.data.docker.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import otus.deryagina.spring.library.data.docker.dao.CommentRepository;
import otus.deryagina.spring.library.data.docker.domain.Book;
import otus.deryagina.spring.library.data.docker.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository")
@Slf4j
@DataJpaTest
@EntityScan("otus.deryagina.spring.library.data.docker.domain")
@ComponentScan(basePackageClasses = CommentRepository.class)
class CommentRepositoryTest {

    private static final long EXPECTED_BOOK_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    @DisplayName("should delete all comments from book, but book should exist")
    void shouldDeleteAllCommentsFromBookButBookShouldStay() {
        commentRepository.deleteAllByBook_Id(EXPECTED_BOOK_ID);
        assertThat(commentRepository.findAllByBook_Id(EXPECTED_BOOK_ID)).isEmpty();
        Assertions.assertThat(entityManager.find(Book.class, EXPECTED_BOOK_ID)).isNotNull();
    }

    @DisplayName("should find all comments to book")
    @Test
    void showAllCommentsToBook() {
        List<Comment> comments = commentRepository.findAllByBook_Id(EXPECTED_BOOK_ID);
        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(EXPECTED_NUMBER_OF_COMMENTS);
        System.out.println("comments:" + comments);
    }
}