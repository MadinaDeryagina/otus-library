package otus.deryagina.spring.library.data.nosql.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.deryagina.spring.library.data.nosql.dao.AuthorRepository;
import otus.deryagina.spring.library.data.nosql.dao.BookRepository;
import otus.deryagina.spring.library.data.nosql.dao.GenreRepository;
import otus.deryagina.spring.library.data.nosql.domain.Author;
import otus.deryagina.spring.library.data.nosql.domain.Genre;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoGenreDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent() && bookRepository.existsByGenresContaining(genre.get())){
            throw new InvalidObjectDeletion("There are books in library containing this genre. Delete it firstly");
        }

    }
}
