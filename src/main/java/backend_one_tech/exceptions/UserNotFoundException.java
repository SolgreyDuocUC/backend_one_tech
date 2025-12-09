package backend_one_tech.exceptions;

public class UserNotFoundException extends RuntimeException {

    // Constructor para mensajes personalizados (String)
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructor específico para IDs
    public UserNotFoundException(Long id) {
        super("Usuario con ID " + id + " no encontrado");
    }

    public UserNotFoundException(String field, String value) {
        super("Usuario con " + field + " '" + value + "' no encontrado");
    }

}
