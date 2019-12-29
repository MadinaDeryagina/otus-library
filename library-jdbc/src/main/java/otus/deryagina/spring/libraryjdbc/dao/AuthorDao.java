package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAuthorsByNames(List<String> authorsName);
    long insert(Author author);
    Author findById(long id);
    List<Author> findAll();
}
