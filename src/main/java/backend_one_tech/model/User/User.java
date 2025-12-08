package backend_one_tech.model.User;

import backend_one_tech.validations.Age.Adult;
import backend_one_tech.validations.RUN.ValidRun;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "run", length = 12, nullable = false, unique = true)
    @ValidRun //Válida que el RUN sea válido
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(name = "apellidos", length = 50, nullable = false)
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$",
            message = "Debe ser un email válido"
    )
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Adult  //Válida que el usuario sea adulto
    private LocalDate fechaNacimiento;


    @Column(name = "direccion", length = 50, nullable = false)
    private String direccion;

    @NotBlank(message = "La región es obligatoria")
    @Column(name = "region", length = 50, nullable = false)
    private String region;

    @NotBlank(message = "La comuna es obligatoria")
    @Column(name = "comuna", length = 50, nullable = false)
    private String comuna;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20, nullable = false)
    private UserRole rol;

    @Column(name = "puntos_level_up", nullable = false)
    private int puntosLevelUp = 0;

    @Column(name = "codigo_referido", length = 50)
    private String codigoReferido;
}

