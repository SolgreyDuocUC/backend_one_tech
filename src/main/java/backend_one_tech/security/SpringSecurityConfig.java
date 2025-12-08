package backend_one_tech.security;

import backend_one_tech.security.filter.JwtAuthenticationFilter;
import backend_one_tech.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Filtro de login en la URL correcta
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authenticationManager());
        jwtAuthFilter.setFilterProcessesUrl("/api/v1/auth/login");

        return http
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll() // registro
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll() // login
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()

                        // Todo lo demás requiere JWT
                        .anyRequest().permitAll() //Cuando usemos token, cambiar por ejempplo: .anyRequest().authenticated()
                )
                .addFilter(jwtAuthFilter) // filtro de login
                .addFilter(new JwtValidationFilter(authenticationManager())) // filtro de validación del token
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}