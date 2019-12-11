package otus.deryagina.spring.libraryjdbc.dao;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    @Override
    public Book findById(long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        Map<Long, Book> map = namedParameterJdbcOperations.query("select  b.id, b.title, a.ID as author_id, a.NAME as author,g.Id as genre_id, g.NAME as genre from BOOKS b inner join BOOKS_AUTHORS_CORRELATION BAC on b.ID = BAC.BOOKID\n" +
                "inner join AUTHORS A on BAC.AUTHORID = A.ID inner join BOOKS_GENRES_CORRELATION BGC on b.ID = BGC.BOOKID\n" +
                "inner join GENRES G on BGC.GENREID = G.ID WHERE b.id= :id", param, new BookResultSetExtractor());
        if (map != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void insert(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Try to insert null book");
        }
        if (book.getGenres() == null || book.getGenres().isEmpty()) {
            throw new IllegalArgumentException("Try to insert book with null or empty genres");
        }
        if( book.getAuthors() == null || book.getAuthors().isEmpty()){
            throw  new IllegalArgumentException("Try to insert book with null or empty authors");
        }




        //TODO: check authors and genres, insert if they not exist, insert correlations, insert book
    }

    private static class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> bookMap = new HashMap<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                Book book = bookMap.get(id);
                if (book == null) {
                    book = new Book(id, resultSet.getString("title"), new ArrayList<>(),
                            new ArrayList<>());
                    bookMap.put(id, book);
                }
                Author currentAuthor = new Author(resultSet.getLong("author_id"), resultSet.getString("author"));
                List<Author> authorsOfBook = book.getAuthors();
                if (!authorsOfBook.contains(currentAuthor)) {
                    authorsOfBook.add(currentAuthor);
                }
                Genre currentGenre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"));
                List<Genre> genresOfBook = book.getGenres();
                if (!genresOfBook.contains(currentGenre)) {
                    genresOfBook.add(currentGenre);
                }

            }
            return bookMap;
        }
    }
}
