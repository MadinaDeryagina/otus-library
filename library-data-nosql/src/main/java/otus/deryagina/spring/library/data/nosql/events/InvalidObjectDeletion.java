package otus.deryagina.spring.library.data.nosql.events;


public class InvalidObjectDeletion extends RuntimeException {

    public InvalidObjectDeletion(String message) {
        super(message);
    }
}
