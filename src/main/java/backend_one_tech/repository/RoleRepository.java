package backend_one_tech.repository;

import backend_one_tech.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Long> {

    Optional<Role> findByName(String name);

}
