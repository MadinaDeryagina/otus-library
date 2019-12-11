package otus.deryagina.spring.libraryjdbc.services;

import otus.deryagina.spring.libraryjdbc.dto.BookDTO;

import java.util.List;

public interface BookService {
    void addBook();
    List<BookDTO> findAllBooks();
    BookDTO findBookById(long id);
}
