package otus.deryagina.spring.library.data.metrics.services;



import otus.deryagina.spring.library.data.metrics.dto.BookDTO;
import otus.deryagina.spring.library.data.metrics.dto.CommentDTO;

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

    BookDTO saveOrUpdate(BookDTO bookDTO);
}
