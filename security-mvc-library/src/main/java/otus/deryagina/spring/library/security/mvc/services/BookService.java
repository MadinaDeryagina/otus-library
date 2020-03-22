package otus.deryagina.spring.library.security.mvc.services;


import otus.deryagina.spring.library.security.mvc.domain.Book;
import otus.deryagina.spring.library.security.mvc.dto.BookDTO;
import otus.deryagina.spring.library.security.mvc.dto.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> findAllBooks();

    Optional<BookDTO> findBookById(long id);

    List<BookDTO> findBooksByTitle(String title);

    void deleteBookById(long id);

    void addCommentToBook(CommentDTO commentDTO) throws IllegalArgumentException;

    List<CommentDTO> showAllCommentsToBook(long bookId);

    void deleteAllCommentsFromBook(long bookId);

    Book saveOrUpdate(BookDTO bookDTO);
}
