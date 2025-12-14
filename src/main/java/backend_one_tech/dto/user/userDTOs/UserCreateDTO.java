package backend_one_tech.dto.user.userDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserCreateDTO {

    private String run;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    private String direccion;
    private String region;
    private String comuna;
    private String genero;

    private Set<Long> roleIds;
}
