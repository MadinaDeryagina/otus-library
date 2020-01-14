package otus.deryagina.spring.libraryjpa.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.libraryjpa.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class GenreDaoJpaImpl implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g",
                Genre.class);
        return query.getResultList();

    }

    @Override
    public List<Genre> findGenresByNames(List<String> names) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where name in :names",
                Genre.class);
        query.setParameter("names", names);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findGenreByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where name = :name",
                Genre.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }


    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            entityManager.persist(genre);
        } else {
            entityManager.merge(genre);
        }
        return genre;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }
}
