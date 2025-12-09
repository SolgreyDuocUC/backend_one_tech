package backend_one_tech.dto.user.userDTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String region;
    private String comuna;
    private List<String> roles;
}
