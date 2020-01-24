package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.library.data.nosql.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    //@EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    //@EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(long bookId);

    //@EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByTitle(String title);
}
