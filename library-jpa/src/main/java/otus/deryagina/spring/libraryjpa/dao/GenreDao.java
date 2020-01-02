package otus.deryagina.spring.libraryjpa.dao;



import otus.deryagina.spring.libraryjpa.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();
    List<Genre> findGenresByNames(List<String> names);
    Genre findGenreByName(String name);
    long insert(Genre genre);
    Genre findById(long id);
}
