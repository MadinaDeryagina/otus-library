package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.library.data.nosql.domain.Comment;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends CrudRepository<Comment,Long> {

    void deleteAllByBook_Id(long bookId);
    List<Comment> findAllByBook_Id(long bookId);
}
