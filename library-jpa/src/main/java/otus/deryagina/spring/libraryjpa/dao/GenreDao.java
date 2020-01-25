package otus.deryagina.spring.libraryjpa.dao;



import otus.deryagina.spring.libraryjpa.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    List<Genre> findGenresByNames(List<String> names);
    Optional<Genre> findGenreByName(String name);
    Genre save(Genre genre);
    Optional<Genre> findById(long id);
}
