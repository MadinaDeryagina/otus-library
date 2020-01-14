package otus.deryagina.spring.libraryjpa.dao;


import otus.deryagina.spring.libraryjpa.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> findAll();

    Optional<Book> findById(long id);

    List<Book> findBooksByTitle(String title);

    Book save(Book book);

    void addAuthorForBook(long bookId, long authorId);

    void deleteAuthorFromBook(long bookId, long authorId);

    void addGenreForBook(long bookId, long genreId);

    void deleteGenreFromBook(long bookId, long genreId);

    void deleteBookById(long id);
}
