package otus.deryagina.spring.library.data.docker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otus.deryagina.spring.library.data.docker.domain.Comment;


import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {

    void deleteAllByBook_Id(long bookId);
    List<Comment> findAllByBook_Id(long bookId);
}
