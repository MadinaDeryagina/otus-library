package otus.deryagina.spring.reactive.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.deryagina.spring.reactive.library.domain.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

   List<Author> findAllByFullNameIn(List<String> names);
   Optional<Author> findAuthorByFullName(String fullName);
}
