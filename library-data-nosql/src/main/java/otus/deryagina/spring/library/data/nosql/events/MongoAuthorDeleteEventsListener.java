package otus.deryagina.spring.library.data.nosql.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.deryagina.spring.library.data.nosql.dao.AuthorRepository;
import otus.deryagina.spring.library.data.nosql.dao.BookRepository;
import otus.deryagina.spring.library.data.nosql.domain.Author;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoAuthorDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent() && bookRepository.existsByAuthorsContaining(author.get())){
            throw new InvalidObjectDeletion("There are books in library containing this author. Delete it firstly");
        }

    }
}
