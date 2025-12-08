package backend_one_tech.services.User;

import java.util.List;

import backend_one_tech.dto.user.UserDTO;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.dto.user.userDTOs.UserUpdateDTO;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO findByRun(String run);

    UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void deleteUser(Long id);
}
