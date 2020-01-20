package otus.deryagina.spring.libraryjpa.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.libraryjpa.domain.Author;
import otus.deryagina.spring.libraryjpa.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class AuthorDaoJpaImpl implements AuthorDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Author> findAuthorsByNames(List<String> authorsNames) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.fullName in :names",
                Author.class);
        query.setParameter("names", authorsNames);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a",
                Author.class);
        return query.getResultList();
    }
}
