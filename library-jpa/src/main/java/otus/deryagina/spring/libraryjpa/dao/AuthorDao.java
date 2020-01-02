package otus.deryagina.spring.libraryjpa.dao;


import otus.deryagina.spring.libraryjpa.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAuthorsByNames(List<String> authorsName);
    Author save(Author author);
    Author findById(long id);
    List<Author> findAll();
}
