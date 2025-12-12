package backend_one_tech.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Role with id " + id + " not found");
    }
}
