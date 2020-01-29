package otus.deryagina.spring.library.data.mvc.services;


import otus.deryagina.spring.library.data.mvc.domain.Book;
import otus.deryagina.spring.library.data.mvc.dto.BookDTO;
import otus.deryagina.spring.library.data.mvc.dto.CommentDTO;

import java.util.List;

public interface BookService {
    Book addAsNewBook(BookDTO bookDTO);

    List<BookDTO> findAllBooks();

    BookDTO findBookById(long id);

    List<BookDTO> findBooksByTitle(String title);

    void updateBook(long id, BookDTO targetInfo);

    void deleteBookById(long id);

    void addCommentToBook(CommentDTO commentDTO) throws IllegalArgumentException;

    List<CommentDTO> showAllCommentsToBook(long bookId);

    void deleteAllCommentsFromBook(long bookId);
}
