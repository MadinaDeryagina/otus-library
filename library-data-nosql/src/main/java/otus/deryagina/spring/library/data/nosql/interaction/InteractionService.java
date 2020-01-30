package otus.deryagina.spring.library.data.nosql.interaction;


import otus.deryagina.spring.library.data.nosql.dto.CommentDTO;

import java.util.List;

public interface InteractionService {
    void askToAddBook();

    void updateBookById(String id);

    void deleteBookById(String id);

    void addCommentToBook(String bookId);

    List<CommentDTO> showBookComments(String bookId);

    void deleteAllCommentsFromBook(String  bookId);
}
