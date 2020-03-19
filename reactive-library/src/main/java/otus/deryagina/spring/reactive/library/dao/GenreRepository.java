package otus.deryagina.spring.reactive.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.deryagina.spring.reactive.library.domain.Genre;
import reactor.core.publisher.Mono;


public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findGenreByName(String name);

}
