package otus.deryagina.spring.library.security.acl.mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.security.acl.mvc.dao.UserRepository;
import otus.deryagina.spring.library.security.acl.mvc.domain.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsDataBaseService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        Set<GrantedAuthority> grantedAuthorities = userEntity.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toSet());

        return new User(userEntity.getUserName(), userEntity.getPassword(), grantedAuthorities);
    }
}
