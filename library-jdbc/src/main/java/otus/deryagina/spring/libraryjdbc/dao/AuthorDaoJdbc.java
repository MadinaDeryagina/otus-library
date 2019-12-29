package otus.deryagina.spring.libraryjdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.libraryjdbc.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Author> findAuthorsByNames(List<String> names) {
        Map<String, List<String>> params = new HashMap<>(1);
        params.put("names", names);
        return namedParameterJdbcOperations.query("select * from AUTHORS where name in (:names)",
                params, new AuthorMapper());

    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getFullName());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into AUTHORS (`name`) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public Author findById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject("select * from AUTHORS where id =:id",
                    params, new AuthorMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Author> findAll() {
        return namedParameterJdbcOperations.query("select * from AUTHORS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
