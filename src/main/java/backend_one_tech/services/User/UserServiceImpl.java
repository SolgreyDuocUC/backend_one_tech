package backend_one_tech.services.User;

import backend_one_tech.dto.user.UserDTO;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.dto.user.userDTOs.UserUpdateDTO;
import backend_one_tech.exceptions.UserNotFoundException;
import backend_one_tech.model.User.User;
import backend_one_tech.model.User.UserRole;
import backend_one_tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE

    @Override
    public UserDTO createUser(UserCreateDTO dto) {

        User user = new User();
        user.setRun(dto.getRun());
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Encode password
        user.setFechaNacimiento(dto.getFechaNacimiento());
        user.setDireccion(dto.getDireccion());
        user.setRegion(dto.getRegion());
        user.setComuna(dto.getComuna());
        user.setRol(UserRole.CLIENTE);
        user.setPuntosLevelUp(0);
        user.setCodigoReferido(null);

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    // READ

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // BUSCAR POR ID

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuario con ID " + id + " no encontrado"));

        return toDTO(user);
    }

    // BUSCAR POR EMAIL

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("No existe usuario con email: " + email));

        return toDTO(user);
    }


    // BUSCAR POR RUN

    @Override
    public UserDTO findByRun(String run) {
        User user = userRepository.findByRun(run)
                .orElseThrow(() ->
                        new UserNotFoundException("No existe usuario con RUN: " + run));

        return toDTO(user);
    }


    // UPDATE

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuario con ID " + id + " no encontrado"));

        // Actualizar solo lo editable
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setDireccion(dto.getDireccion());
        user.setRegion(dto.getRegion());
        user.setComuna(dto.getComuna());

        User updated = userRepository.save(user);

        return toDTO(updated);
    }


    // DELETE

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario con ID " + id + " no existe");
        }
        userRepository.deleteById(id);
    }


    // ENTITY TO DTO

    private UserDTO toDTO(User user) {
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
