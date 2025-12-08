package backend_one_tech.security;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    // Clave secreta para firmar el token. DEBE ser segura y lo suficientemente larga.
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    // Prefijo que se añade al token en la cabecera HTTP.
    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    // Nombre de la cabecera HTTP donde se enviará el token.
    public static final String HEADER_STRING = "Authorization";

    public static final String CONTENT_TYPE = "application/json";
}