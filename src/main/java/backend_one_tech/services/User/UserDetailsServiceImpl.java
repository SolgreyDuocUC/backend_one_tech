package backend_one_tech.services.User;

import backend_one_tech.model.User.User;
import backend_one_tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String run) throws UsernameNotFoundException {

        User user = userRepository.findByRun(run)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No existe usuario con RUN: " + run)
                );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRol().name());

        return new org.springframework.security.core.userdetails.User(
                user.getRun(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                List.of(authority)
        );
    }
}

