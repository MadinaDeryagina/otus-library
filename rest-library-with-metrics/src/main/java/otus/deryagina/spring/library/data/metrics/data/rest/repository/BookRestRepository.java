package otus.deryagina.spring.library.data.metrics.data.rest.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.library.data.metrics.domain.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rest_books")
public interface BookRestRepository extends PagingAndSortingRepository<Book,Long> {

    @EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    @EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByOrderByTitleAsc();

    @EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(long bookId);

    @EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByTitle(String title);
}
