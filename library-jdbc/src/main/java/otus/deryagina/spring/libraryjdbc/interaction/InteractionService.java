package otus.deryagina.spring.libraryjdbc.interaction;



public interface InteractionService {
    void askToAddBook();

    void updateBookById(Long id);

    void deleteBookById(Long id);
}
