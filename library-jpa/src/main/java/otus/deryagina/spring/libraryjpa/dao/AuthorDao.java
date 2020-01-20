package otus.deryagina.spring.libraryjpa.dao;


import otus.deryagina.spring.libraryjpa.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAuthorsByNames(List<String> authorsName);
    Author save(Author author);
    Optional<Author> findById(long id);
    List<Author> findAll();
}
