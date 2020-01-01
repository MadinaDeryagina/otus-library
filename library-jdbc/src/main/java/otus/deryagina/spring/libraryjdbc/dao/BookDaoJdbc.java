package otus.deryagina.spring.libraryjdbc.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.BookGenreRelation;
import otus.deryagina.spring.libraryjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final GenreDao genreDao;

    @Override
    public List<Book> findAll() {
        Map<Long, Book> map = namedParameterJdbcOperations.query("select  b.id, b.title, a.ID as author_id, a.NAME as author from BOOKS b inner join BOOKS_AUTHORS_CORRELATION BAC on b.ID = BAC.BOOKID\n" +
                "inner join AUTHORS A on BAC.AUTHORID = A.ID", new BookAuthorsResultSetExtractor());
        List<BookGenreRelation> bookGenreRelations = getAllBookGenreRelations();
        List<Genre> genres = genreDao.findAll();
        mergeBookWithGenreInfo(map,genres,bookGenreRelations);
        if (map != null) {
            return new ArrayList<>(map.values());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Book findById(long id) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("id", id);
        Map<Long, Book> map = namedParameterJdbcOperations.query("select  b.id, b.title, a.ID as author_id, a.NAME as author,g.Id as genre_id, g.NAME as genre from BOOKS b inner join BOOKS_AUTHORS_CORRELATION BAC on b.ID = BAC.BOOKID\n" +
                "inner join AUTHORS A on BAC.AUTHORID = A.ID inner join BOOKS_GENRES_CORRELATION BGC on b.ID = BGC.BOOKID\n" +
                "inner join GENRES G on BGC.GENREID = G.ID WHERE b.id= :id", param, new BookAuthorsGenresResultSetExtractor());
        if (map != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", title);
        Map<Long, Book> map = namedParameterJdbcOperations.query("select  b.id, b.title, a.ID as author_id, a.NAME as author,g.Id as genre_id, g.NAME as genre from BOOKS b inner join BOOKS_AUTHORS_CORRELATION BAC on b.ID = BAC.BOOKID\n" +
                "inner join AUTHORS A on BAC.AUTHORID = A.ID inner join BOOKS_GENRES_CORRELATION BGC on b.ID = BGC.BOOKID\n" +
                "inner join GENRES G on BGC.GENREID = G.ID WHERE b.TITLE= :title", param, new BookAuthorsGenresResultSetExtractor());
        if (map != null) {
            return new ArrayList<>(map.values());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public long insert(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Try to insert null book");
        }
        if (book.getGenres() == null || book.getGenres().isEmpty()) {
            throw new IllegalArgumentException("Try to insert book with null or empty genres");
        }
        if( book.getAuthors() == null || book.getAuthors().isEmpty()){
            throw  new IllegalArgumentException("Try to insert book with null or empty authors");
        }
        //insert new book , get it's id and insert all correlations
        //insert in book table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into BOOKS (`title`) values (:title)", params, kh);
        long newBookId  = kh.getKey().longValue();
        //insert correlations
        //books_authors_correlation
        for (Author author:
             book.getAuthors()) {
            addAuthorForBook(newBookId,author.getId());
        }
        //books_genres_correlation
        for (Genre genre:
                book.getGenres()) {
            addGenreForBook(newBookId,genre.getId());
        }

        return newBookId;

    }

    @Override
    public void updateBookTitle(long id, String newTitle) {
        Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        params.put("title",newTitle);
        namedParameterJdbcOperations.update("update books set title = :title where id = :id",params);
    }

    @Override
    public void addAuthorForBook(long bookId, long authorId) {
        Map<String, Object> bookAuthorParams = new HashMap<>(2);
        bookAuthorParams.put("bookId", bookId);
        bookAuthorParams.put("authorId", authorId);
        namedParameterJdbcOperations.update("insert into books_authors_correlation (bookId, authorId) values (:bookId, :authorId)"
                ,bookAuthorParams);
    }

    @Override
    public void deleteAuthorFromBook(long bookId, long authorId) {
        Map<String, Object> bookAuthorParams = new HashMap<>(2);
        bookAuthorParams.put("bookId", bookId);
        bookAuthorParams.put("authorId", authorId);
        namedParameterJdbcOperations.update("delete from books_authors_correlation where BOOKID = :bookId and AUTHORID = :authorId"
                ,bookAuthorParams);
    }

    @Override
    public void addGenreForBook(long bookId, long genreId) {
        Map<String, Object> bookGenreParams = new HashMap<>(2);
        bookGenreParams.put("bookId", bookId);
        bookGenreParams.put("genreId", genreId);
        namedParameterJdbcOperations.update("insert into books_genres_correlation (bookId, genreId) values (:bookId, :genreId)"
                ,bookGenreParams);
    }

    @Override
    public void deleteGenreFromBook(long bookId, long genreId) {
        Map<String, Object> bookGenreParams = new HashMap<>(2);
        bookGenreParams.put("bookId", bookId);
        bookGenreParams.put("genreId", genreId);
        namedParameterJdbcOperations.update("delete from books_genres_correlation where BOOKID = :bookId and GENREID = :genreId"
                ,bookGenreParams);
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("bookId", id);
        namedParameterJdbcOperations.update("delete from books where id = :bookId"
                ,param);
    }

    private List<BookGenreRelation> getAllBookGenreRelations() {
        return namedParameterJdbcOperations.query("select bookId, genreId from BOOKS_GENRES_CORRELATION sc order by BOOKID, GENREID",
                (rs, i) -> new BookGenreRelation(rs.getLong(1), rs.getLong(2)));
    }

    private void mergeBookWithGenreInfo(Map<Long, Book> bookMap, List<Genre> genres, List<BookGenreRelation> relations) {
        Map<Long, Genre> genreMap = genres.stream().collect(Collectors.toMap(Genre::getId, c -> c));
        relations.forEach(r -> {
            if (bookMap.containsKey(r.getBookId()) && genreMap.containsKey(r.getGenreId())) {
                bookMap.get(r.getBookId()).getGenres().add(genreMap.get(r.getGenreId()));
            }
        });
    }

    private static class BookAuthorsGenresResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> bookMap = new HashMap<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                addBookAuthorInfo(resultSet,bookMap);
                Book book = bookMap.get(id);
                Genre currentGenre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"));
                List<Genre> genresOfBook = book.getGenres();
                if (!genresOfBook.contains(currentGenre)) {
                    genresOfBook.add(currentGenre);
                }

            }
            return bookMap;
        }
    }
    private static class BookAuthorsResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> bookMap = new HashMap<>();
            while (resultSet.next()) {
                addBookAuthorInfo(resultSet, bookMap);
            }
            return bookMap;
        }



    }
    private static void addBookAuthorInfo(ResultSet resultSet, Map<Long, Book> bookMap) throws SQLException {
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
    }

}
