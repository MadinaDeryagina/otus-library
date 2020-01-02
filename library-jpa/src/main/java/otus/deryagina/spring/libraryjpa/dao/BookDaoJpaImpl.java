package otus.deryagina.spring.libraryjpa.dao;

import otus.deryagina.spring.libraryjpa.domain.Book;

import java.util.List;

public class BookDaoJpaImpl implements BookDao {
    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book findById(long id) {
        return null;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return null;
    }

    @Override
    public long insert(Book book) {
        return 0;
    }

    @Override
    public void updateBookTitle(long id, String newTitle) {

    }

    @Override
    public void addAuthorForBook(long bookId, long authorId) {

    }

    @Override
    public void deleteAuthorFromBook(long bookId, long authorId) {

    }

    @Override
    public void addGenreForBook(long bookId, long genreId) {

    }

    @Override
    public void deleteGenreFromBook(long bookId, long genreId) {

    }

    @Override
    public void deleteBookById(long id) {

    }
}
