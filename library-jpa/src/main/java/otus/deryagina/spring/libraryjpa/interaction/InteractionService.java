package otus.deryagina.spring.libraryjpa.interaction;



public interface InteractionService {
    void askToAddBook();

    void updateBookById(long id);

    void deleteBookById(long id);
}
