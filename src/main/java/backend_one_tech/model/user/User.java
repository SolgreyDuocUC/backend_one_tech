package backend_one_tech.model.user;

import backend_one_tech.model.rol.Role;
import backend_one_tech.validations.Age.Adult;
import backend_one_tech.validations.RUN.ValidRun;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "run", length = 12, nullable = false, unique = true)
    @ValidRun
    private String run;

    @NotBlank
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank
    @Column(name = "apellidos", length = 50, nullable = false)
    private String apellidos;

    @NotBlank
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$"
    )
    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Adult
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "direccion", length = 50, nullable = false)
    private String direccion;

    @NotBlank
    @Column(name = "region", length = 50, nullable = false)
    private String region;

    @NotBlank
    @Column(name = "comuna", length = 50, nullable = false)
    private String comuna;

    @Column(name = "puntos_level_up", nullable = false)
    private int puntosLevelUp = 0;

    @Column(name = "codigo_referido", length = 50)
    private String codigoReferido;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = true)
    private Genero genero = Genero.SIN_ESPECIFICAR;

    @JsonIgnoreProperties({"users"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"user_id", "role_id"})
            }
    )
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.enabled = true;
        if (this.genero == null) {
            this.genero = Genero.SIN_ESPECIFICAR;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}