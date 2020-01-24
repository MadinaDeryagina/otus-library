package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.library.data.nosql.domain.Author;

import java.util.List;


@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAllByFullNameIn(List<String> names);

}
