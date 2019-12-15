package otus.deryagina.spring.libraryjdbc.dao;

import otus.deryagina.spring.libraryjdbc.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(long id);
    List<Book> findBooksByTitle(String title);
    void insert(Book book);
}
