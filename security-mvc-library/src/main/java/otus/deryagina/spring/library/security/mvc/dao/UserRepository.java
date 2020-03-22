package otus.deryagina.spring.library.security.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.deryagina.spring.library.security.mvc.domain.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByUserName(String  userName);
}
