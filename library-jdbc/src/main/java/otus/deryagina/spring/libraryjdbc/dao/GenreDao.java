package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();
    List<Genre> findGenresByNames(List<String> names);
    Genre findGenreByName(String name);
    long insert(Genre genre);
    Genre findById(long id);
}
