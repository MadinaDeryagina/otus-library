package otus.deryagina.spring.libraryjdbc.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Book> findAll() {
        Map<Long, Book> map = namedParameterJdbcOperations.query("select  b.id, b.title, a.ID as author_id, a.NAME as author,g.Id as genre_id, g.NAME as genre from BOOKS b inner join BOOKS_AUTHORS_CORRELATION BAC on b.ID = BAC.BOOKID\n" +
                "inner join AUTHORS A on BAC.AUTHORID = A.ID inner join BOOKS_GENRES_CORRELATION BGC on b.ID = BGC.BOOKID\n" +
                "inner join GENRES G on BGC.GENREID = G.ID", new BookResultSetExtractor());
        if (map != null) {
            return new ArrayList<>(map.values());
        } else {
            return new ArrayList<>();
        }
    }

    private static class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> bookMap = new HashMap<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                Book book = bookMap.get(id);
                if (book == null) {
                    book = new Book(id, resultSet.getString("title"), new ArrayList<Author>(),
                            new ArrayList<Genre>());
                    bookMap.put(id, book);
                }
                Author currentAuthor = new Author(resultSet.getLong("author_id"), resultSet.getString("author"));
                List<Author> authorsOfBook = book.getAuthors();
                if (!authorsOfBook.contains(currentAuthor)) {
                    authorsOfBook.add(currentAuthor);
                }
                Genre currentGenre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"));
                List<Genre> genresOfBook = book.getGenres();
                if( !genresOfBook.contains(currentGenre) ){
                    genresOfBook.add(currentGenre);
                }

            }
            return bookMap;
        }
    }
}
