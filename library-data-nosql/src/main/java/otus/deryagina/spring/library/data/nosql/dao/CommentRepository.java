package otus.deryagina.spring.library.data.nosql.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.deryagina.spring.library.data.nosql.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {

    void deleteAllByBook_Id(String bookId);
    List<Comment> findAllByBook_Id(String bookId);
}
