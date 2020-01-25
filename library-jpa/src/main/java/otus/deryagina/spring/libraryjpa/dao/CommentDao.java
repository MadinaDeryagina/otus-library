package otus.deryagina.spring.libraryjpa.dao;

import otus.deryagina.spring.libraryjpa.domain.Comment;

import java.util.List;

public interface CommentDao {

    Comment save(Comment comment);

    void deleteAllCommentsFromBook(long bookId);

    List<Comment> findAllCommentsToBook(long bookId);

}
