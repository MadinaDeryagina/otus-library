package otus.deryagina.spring.reactive.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.deryagina.spring.reactive.library.domain.Author;
import otus.deryagina.spring.reactive.library.domain.Book;
import otus.deryagina.spring.reactive.library.domain.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book,String> {

    List<Book> findAllByTitle(String title);
    //Flux<Book> findByAuthorsContaining(Mono<Author> authorMono);
    Mono<Boolean> existsByAuthorsContaining(Mono<Author> author);
    Mono<Boolean> existsByAuthorsContaining(Author author);
    Flux<Book> findAllByAuthorsContaining(Author author);
    boolean existsByGenresContaining(Mono<Genre> genre);
}
