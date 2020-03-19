package otus.deryagina.spring.reactive.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.deryagina.spring.reactive.library.domain.Author;
import reactor.core.publisher.Mono;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findAuthorByFullName(String fullName);
}
