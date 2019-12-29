package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;

import java.util.List;

public interface BookDao {
    List<Book> findAll();

    Book findById(long id);

    List<Book> findBooksByTitle(String title);

    long insert(Book book);

    void updateBookTitle(long id, String newTitle);

    void addAuthorForBook(long bookId, long authorId);

    void deleteAuthorFromBook(long bookId, long authorId);

    void addGenreForBook(long bookId, long genreId);

    void deleteGenreFromBook(long bookId, long genreId);

    void deleteBookById(long id);
}
