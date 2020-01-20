package otus.deryagina.spring.libraryjpa.services;


import otus.deryagina.spring.libraryjpa.domain.Book;
import otus.deryagina.spring.libraryjpa.dto.BookDTO;

import java.util.List;

public interface BookService {
    Book addAsNewBook(BookDTO bookDTO);

    List<BookDTO> findAllBooks();

    BookDTO findBookById(long id);

    List<BookDTO> findBooksByTitle(String title);

    void updateBook(long id, BookDTO targetInfo);

    void deleteBookById(long id);
}
