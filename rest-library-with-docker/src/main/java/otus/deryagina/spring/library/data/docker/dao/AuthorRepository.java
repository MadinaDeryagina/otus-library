package otus.deryagina.spring.library.data.docker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.library.data.docker.domain.Author;


import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByFullNameIn(List<String> names);

}
