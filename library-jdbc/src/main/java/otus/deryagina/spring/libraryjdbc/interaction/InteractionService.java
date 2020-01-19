package otus.deryagina.spring.libraryjdbc.interaction;



public interface InteractionService {
    void askToAddBook();

    void updateBookById(long id);

    void deleteBookById(long id);
}
