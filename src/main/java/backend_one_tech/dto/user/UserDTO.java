package backend_one_tech.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/*
Funciones del DTO
    - Responder datos de usuario al frontend.
    - No incluye password.
    - Evitar exponer datos sensibles:

Obligación de buenas prácticas.
*/

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String run;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String region;
    private String comuna;
    private String genero;
    private List<String> roles;
    private Integer puntosLevelUp;
    private String codigoReferido;
}