package otus.deryagina.spring.library.data.nosql.interaction;


import otus.deryagina.spring.library.data.nosql.dto.CommentDTO;

import java.util.List;

public interface InteractionService {
    void askToAddBook();

    void updateBookById(long id);

    void deleteBookById(long id);

    void addCommentToBook(long bookId);

    List<CommentDTO> showBookComments(long bookId);

    void deleteAllCommentsFromBook(long bookId);
}
