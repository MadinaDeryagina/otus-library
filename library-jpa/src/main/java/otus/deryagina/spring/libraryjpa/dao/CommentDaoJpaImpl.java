package otus.deryagina.spring.libraryjpa.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.libraryjpa.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDaoJpaImpl implements CommentDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }
    }

    @Override
    public void deleteAllCommentsFromBook(long bookId) {
        Query query = entityManager.createQuery("delete " +
                "from Comment c " +
                "where c.book.id = :bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }

    @Override
    public List<Comment> findAllCommentsToBook(long bookId) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book.id =: bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
