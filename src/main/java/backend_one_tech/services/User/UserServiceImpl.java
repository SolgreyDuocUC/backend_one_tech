package backend_one_tech.services.User;

import backend_one_tech.dto.user.UserDTO;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.dto.user.userDTOs.UserUpdateDTO;
import backend_one_tech.exceptions.UserNotFoundException;
import backend_one_tech.model.User.User;
import backend_one_tech.model.User.UserRole;
import backend_one_tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public UserDTO findById(Long id) {
        User user = (User) userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return toDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return toDTO(user);
    }

    @Override
    public UserDTO findByRun(String run) {
        User user = userRepository.findByRun(run)
                .orElseThrow(() -> new UserNotFoundException("RUN: " + run));

        return toDTO(user);
    }

    @Override
    public UserDTO createUser(UserCreateDTO dto) {

        User user = new User();
        user.setRun(dto.getRun());
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // sin encoder por ahora
        user.setFechaNacimiento(dto.getFechaNacimiento());
        user.setDireccion(dto.getDireccion());
        user.setRegion(dto.getRegion());
        user.setComuna(dto.getComuna());
        user.setRol(UserRole.CLIENTE);

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setDireccion(dto.getDireccion());
        user.setRegion(dto.getRegion());
        user.setComuna(dto.getComuna());

        User updated;
        updated = userRepository.save(user);
        return toDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User findEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setRun(user.getRun());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setEmail(user.getEmail());
        dto.setFechaNacimiento(user.getFechaNacimiento());
        dto.setDireccion(user.getDireccion());
        dto.setRegion(user.getRegion());
        dto.setComuna(user.getComuna());
        dto.setRol(user.getRol());
        dto.setPuntosLevelUp(user.getPuntosLevelUp());
        dto.setCodigoReferido(user.getCodigoReferido());
        return dto;
    }
}
