package backend_one_tech.repository;


import backend_one_tech.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar por email
    Optional<User> findByEmail(String email);

    // Buscar por RUN
    Optional<User> findByRun(String run);

    // Verificar si email ya existe (útil en validaciones)
    boolean existsByEmail(String email);

    // Verificar si RUN ya existe
    boolean existsByRun(String run);

}

