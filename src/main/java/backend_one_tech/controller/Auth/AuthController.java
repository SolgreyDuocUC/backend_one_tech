package backend_one_tech.controller.Auth;

import backend_one_tech.dto.Auth.AuthResponse;
import backend_one_tech.dto.Auth.LoginRequest;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.model.user.User;
import backend_one_tech.security.JwtUtil;
import backend_one_tech.services.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // -------------------- Registro --------------------

    @PostMapping("/register")
    @Operation(
            summary = "Registrar usuario",
            description = "Crea un nuevo usuario en el sistema. Este endpoint está diseñado para el flujo de registro inicial.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos o usuario ya existente"
                    )
            }
    )
    public ResponseEntity<?> register(@RequestBody UserCreateDTO dto) {
        User user = userService.createUserEntityForAuth(dto);
        return ResponseEntity.ok("Usuario registrado correctamente con email: " + user.getEmail());
    }

    // -------------------- Login --------------------

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario con su email y contraseña, generando tokens JWT de acceso y refresco.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales incorrectas"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario no existe"
                    )
            }
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findEntityByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        if (!userService.matchesPassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        // Generar JWT usando el bean
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }
}
