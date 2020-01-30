package otus.deryagina.spring.library.data.nosql.services;


import otus.deryagina.spring.library.data.nosql.domain.Book;
import otus.deryagina.spring.library.data.nosql.dto.BookDTO;
import otus.deryagina.spring.library.data.nosql.dto.CommentDTO;

import java.util.List;

public interface BookService {
    Book addAsNewBook(BookDTO bookDTO);

    List<BookDTO> findAllBooks();

    BookDTO findBookById(String id);

    List<BookDTO> findBooksByTitle(String title);

    void updateBook(String id, BookDTO targetInfo);

    void deleteBookById(String id);

    boolean isExistsBookById(String bookId);

    void addCommentToBook(CommentDTO commentDTO) throws IllegalArgumentException;

    List<CommentDTO> showAllCommentsToBook(String bookId);

    void deleteAllCommentsFromBook(String bookId);
}
