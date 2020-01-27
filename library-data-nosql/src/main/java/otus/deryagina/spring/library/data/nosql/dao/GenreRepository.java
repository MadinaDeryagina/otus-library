package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.deryagina.spring.library.data.nosql.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAllByNameIn(List<String> names);
    Optional<Genre> findByName(String name);
}
