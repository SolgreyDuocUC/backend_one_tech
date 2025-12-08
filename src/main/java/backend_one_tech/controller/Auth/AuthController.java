package backend_one_tech.controller.Auth;

import backend_one_tech.dto.Auth.LoginRequest;
import backend_one_tech.model.User.User;
import backend_one_tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null)
            return ResponseEntity.status(401).body("Correo no existe");

        if (!user.getPassword().equals(request.getPassword()))
            return ResponseEntity.status(401).body("Contraseña incorrecta");

        return ResponseEntity.ok("Login correcto");
    }
}
