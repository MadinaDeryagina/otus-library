package otus.deryagina.spring.reactive.library.handler;


public class InvalidObjectDeletion extends RuntimeException {

    public InvalidObjectDeletion(String message) {
        super(message);
    }
}
