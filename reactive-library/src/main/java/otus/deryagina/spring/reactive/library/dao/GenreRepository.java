package otus.deryagina.spring.reactive.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.deryagina.spring.reactive.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    List<Genre> findAllByNameIn(List<String> names);
    Optional<Genre> findByName(String name);
}
