package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Book;

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

    void deleteAllAuthorsFromBook(long bookId);

    void addNewAuthorsToBook(long bookId, List<Long> targetInfoAuthors);

    void deleteAllGenresFromBook(long bookId);

    void addNewGenresToBook(long bookId, List<Long> targetGenresIds);
}
