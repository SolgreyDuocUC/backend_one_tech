package backend_one_tech.repository;

import backend_one_tech.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRun(String run);

    boolean existsByEmail(String email);

    boolean existsByRun(String run);
}
