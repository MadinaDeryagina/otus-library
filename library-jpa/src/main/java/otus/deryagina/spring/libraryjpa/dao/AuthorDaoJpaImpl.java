package otus.deryagina.spring.libraryjpa.dao;

import org.springframework.stereotype.Repository;
import otus.deryagina.spring.libraryjpa.domain.Author;

import java.util.List;

@Repository
public class AuthorDaoJpaImpl implements AuthorDao {
    @Override
    public List<Author> findAuthorsByNames(List<String> authorsName) {
        return null;
    }

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public Author findById(long id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }
}
