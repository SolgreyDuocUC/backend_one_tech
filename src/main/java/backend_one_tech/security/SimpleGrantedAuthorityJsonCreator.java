package backend_one_tech.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public static SimpleGrantedAuthority create(@JsonProperty("authority") String authority) {
        return new SimpleGrantedAuthority(authority);
    }
}
