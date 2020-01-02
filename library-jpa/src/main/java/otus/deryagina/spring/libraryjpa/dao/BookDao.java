package otus.deryagina.spring.libraryjpa.dao;


import otus.deryagina.spring.libraryjpa.domain.Book;

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
