package backend_one_tech.services.User;

import backend_one_tech.dto.user.UserDTO;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.dto.user.userDTOs.UserUpdateDTO;
import backend_one_tech.model.user.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO findByRun(String run);

    UserDTO createUser(UserCreateDTO dto);

    UserDTO updateUser(Long id, UserUpdateDTO dto);

    void deleteUser(Long id);

    User findEntityByEmail(String email);

    UserDTO toDTO(User user);

    boolean matchesPassword(String raw, String encoded);

    User createUserEntityForAuth(UserCreateDTO dto); // ← AHORA SÍ DEVUELVE User
}
