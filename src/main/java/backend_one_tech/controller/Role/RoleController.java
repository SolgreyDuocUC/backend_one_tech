package backend_one_tech.controller.Role;

import backend_one_tech.dto.role.RoleDTO;
import backend_one_tech.dto.role.roleDTOS.RoleCreateDTO;
import backend_one_tech.dto.role.roleDTOS.RoleUpdateDTO;
import backend_one_tech.services.Role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin("*")
@Tag(name = "Gestión de Roles", description = "CRUD para gestión de roles de One Tech")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Listar roles", description = "Obtiene la lista completa de roles registrados.")
    public ResponseEntity<List<RoleDTO>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping
    @Operation(summary = "Crear roles", description = "Crea Roles detro de la base de datos y el backend")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleCreateDTO dto) {
        return ResponseEntity.ok(roleService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol por ID", description = "Actualiza el rol con el ID indicado")
    public ResponseEntity<RoleDTO> update(
            @PathVariable Long id,
            @RequestBody RoleUpdateDTO dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene el rol con el ID indicado")

    public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminarrol por ID", description = "Elimina el rol con el ID indicado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
