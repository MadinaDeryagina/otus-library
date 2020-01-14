package otus.deryagina.spring.libraryjpa.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.libraryjpa.domain.Book;
import otus.deryagina.spring.libraryjpa.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Repository
public class BookDaoJpaImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where id = :id",
                Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where title = :title",
                Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
        } else {
            entityManager.merge(book);
        }
        return book;
    }

    @Override
    public void addAuthorForBook(long bookId, long authorId) {

    }

    @Override
    public void deleteAuthorFromBook(long bookId, long authorId) {

    }

    @Override
    public void addGenreForBook(long bookId, long genreId) {

    }

    @Override
    public void deleteGenreFromBook(long bookId, long genreId) {

    }

    @Override
    public void deleteBookById(long id) {

    }
}
