package otus.deryagina.spring.libraryjpa.dao;

import otus.deryagina.spring.libraryjpa.domain.Genre;

import java.util.List;

public class GenreDaoJpaImpl implements GenreDao {
    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public List<Genre> findGenresByNames(List<String> names) {
        return null;
    }

    @Override
    public Genre findGenreByName(String name) {
        return null;
    }

    @Override
    public long insert(Genre genre) {
        return 0;
    }

    @Override
    public Genre findById(long id) {
        return null;
    }
}
