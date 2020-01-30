package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.deryagina.spring.library.data.nosql.domain.Author;
import otus.deryagina.spring.library.data.nosql.domain.Book;
import otus.deryagina.spring.library.data.nosql.domain.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String> {

    List<Book> findAllByTitle(String title);
     boolean existsByAuthorsContaining(Author author);
     boolean existsByGenresContaining(Genre genre);
}
