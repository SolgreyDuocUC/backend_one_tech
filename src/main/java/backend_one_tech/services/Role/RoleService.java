package backend_one_tech.services.Role;

import backend_one_tech.dto.role.RoleDTO;
import backend_one_tech.dto.role.roleDTOS.RoleCreateDTO;
import backend_one_tech.dto.role.roleDTOS.RoleUpdateDTO;

import java.util.List;

public interface RoleService {

    RoleDTO create(RoleCreateDTO dto);

    RoleDTO update(Long id, RoleUpdateDTO dto);

    RoleDTO findById(Long id);

    List<RoleDTO> findAll();

    void delete(Long id);
}

