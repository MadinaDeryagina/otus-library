package otus.deryagina.spring.libraryjdbc.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.query("select * from genres",
                new GenreMapper());
    }

    @Override
    public List<Genre> findGenresByNames(List<String> names) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("names", names);
        return namedParameterJdbcOperations.query("select * from genres where name in (:names)",
                params, new GenreMapper());
    }

    @Override
    public Genre findGenreByName(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        Genre genre;
        try {
            genre = namedParameterJdbcOperations.queryForObject("select * from genres where name =:name", params,
                    new GenreMapper());
            return genre;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}

