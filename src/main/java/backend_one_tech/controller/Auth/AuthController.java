package backend_one_tech.controller.Auth;

import backend_one_tech.dto.Auth.AuthResponse;
import backend_one_tech.dto.Auth.LoginRequest;
import backend_one_tech.dto.user.userDTOs.UserCreateDTO;
import backend_one_tech.model.user.User;
import backend_one_tech.security.JwtUtil;
import backend_one_tech.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil; // <- inyectamos JwtUtil como bean

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO dto) {
        User user = userService.createUserEntityForAuth(dto);
        return ResponseEntity.ok("Usuario registrado correctamente con email: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findEntityByEmail(request.getEmail());

        if (!userService.matchesPassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        // Generar JWT usando el bean
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }
}

