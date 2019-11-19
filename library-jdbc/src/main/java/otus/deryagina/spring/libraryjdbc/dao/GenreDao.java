package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();
}
