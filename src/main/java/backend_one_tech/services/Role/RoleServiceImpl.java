package backend_one_tech.services.Role;

import backend_one_tech.dto.role.RoleDTO;
import backend_one_tech.dto.role.roleDTOS.RoleCreateDTO;
import backend_one_tech.dto.role.roleDTOS.RoleUpdateDTO;
import backend_one_tech.exceptions.RoleNotFoundException;
import backend_one_tech.model.rol.Role;
import backend_one_tech.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    @Override
    public RoleDTO create(RoleCreateDTO dto) {
        Role role = new Role(dto.getName());
        Role saved = roleRepository.save(role);
        return toDTO(saved);
    }

    @Override
    public RoleDTO update(Long id, RoleUpdateDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        role.setName(dto.getName());
        return toDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        return toDTO(role);
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException(id);
        }
        roleRepository.deleteById(id);
    }
}
