package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.deryagina.spring.library.data.nosql.domain.Author;

import java.util.List;


public interface AuthorRepository extends MongoRepository<Author, String> {

   List<Author> findAllByFullNameIn(List<String> names);

}
