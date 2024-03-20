package bg.sofuni.booksserver.repository;

import bg.sofuni.booksserver.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {


    Optional<AuthorEntity> findByName(String name);
}
