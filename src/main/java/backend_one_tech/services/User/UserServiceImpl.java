package backend_one_tech.services.User;

import backend_one_tech.dto.user.UserDTO;
import backend_one_tech.dto.user.userDTOs.ChangePasswordDTO;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.dto.user.userDTOs.UserUpdateDTO;
import backend_one_tech.exceptions.UserNotFoundException;
import backend_one_tech.model.rol.Role;
import backend_one_tech.model.user.Genero;
import backend_one_tech.model.user.User;
import backend_one_tech.repository.RoleRepository;
import backend_one_tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return toDTO(
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @Override
    public UserDTO findByEmail(String email) {
        return toDTO(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(email))
        );
    }

    @Override
    public UserDTO findByRun(String run) {
        return toDTO(
                userRepository.findByRun(run)
                        .orElseThrow(() -> new UserNotFoundException("RUN: " + run))
        );
    }

    @Override
    public UserDTO createUser(UserCreateDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        if (userRepository.existsByRun(dto.getRun())) {
            throw new RuntimeException("RUN already in use");
        }

        User user = buildUserFromDTO(dto);

        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = dto.getRoleIds().stream()
                    .map(id -> roleRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Rol no existe con ID: " + id)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        } else {
            Role clienteRole = roleRepository.findByName("CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Rol CLIENTE no existe"));
            user.getRoles().add(clienteRole);
        }

        return toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (dto.getNombre() != null) user.setNombre(dto.getNombre());
        if (dto.getApellidos() != null) user.setApellidos(dto.getApellidos());
        if (dto.getDireccion() != null) user.setDireccion(dto.getDireccion());
        if (dto.getRegion() != null) user.setRegion(dto.getRegion());
        if (dto.getComuna() != null) user.setComuna(dto.getComuna());
        if (dto.getGenero() != null) user.setGenero(Genero.valueOf(dto.getGenero()));

        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = dto.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Rol no existe con ID: " + roleId)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return toDTO(userRepository.save(user));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
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
    public boolean matchesPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }

    @Override
    public User createUserEntityForAuth(UserCreateDTO dto) {
        return userRepository.save(buildUserFromDTO(dto));
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
        dto.setGenero(user.getGenero().name());
        dto.setPuntosLevelUp(user.getPuntosLevelUp());
        dto.setCodigoReferido(user.getCodigoReferido());
        dto.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    private User buildUserFromDTO(UserCreateDTO dto) {

        User user = new User();
        user.setRun(dto.getRun());
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFechaNacimiento(dto.getFechaNacimiento());
        user.setDireccion(dto.getDireccion());
        user.setRegion(dto.getRegion());
        user.setComuna(dto.getComuna());
        user.setGenero(
                dto.getGenero() != null
                        ? Genero.valueOf(dto.getGenero())
                        : Genero.SIN_ESPECIFICAR
        );

        return user;
    }
}
