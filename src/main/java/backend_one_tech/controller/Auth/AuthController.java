package backend_one_tech.controller.Auth;

import backend_one_tech.dto.Auth.AuthResponse;
import backend_one_tech.dto.Auth.LoginRequest;
import backend_one_tech.dto.user.UserDTO;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema. Si no se especifican roles, se asigna 'USER' por defecto.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos o el rol especificado no existe"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO dto) {
        UserDTO userDTO = userService.createUser(dto);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario con su email y contraseña, y devuelve un token de acceso y de refresco.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales incorrectas"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findEntityByEmail(request.getEmail());

        if (!userService.matchesPassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @Operation(
            summary = "Refrescar el token",
            description = "Mantiene el token del usuario para que pueda mantener la sesión activa"
    )
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token es requerido");
        }

        // Extraer el email
        String userEmail;
        try {
            userEmail = jwtUtil.extractEmail(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Refresh token inválido o manipulado");
        }

        // Validar refresh token
        if (!jwtUtil.isTokenValid(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token expirado");
        }

        // Obtener usuario
        User user = userService.findEntityByEmail(userEmail);

        // Crear nuevo access token
        String newAccessToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(newAccessToken, refreshToken)
        );
    }

}
