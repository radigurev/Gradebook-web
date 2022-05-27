package Gradebook.service.impl;

import Gradebook.model.entity.Users;
import Gradebook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradebookUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public GradebookUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepository.findByEmail(username).orElseThrow(() ->  new UsernameNotFoundException("User with "+username+" does not exist"));

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(Users user) {

        List<GrantedAuthority> authorities= user
                .getRole().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_"+r.getRole().name()))
                .collect(Collectors.toList());

        System.out.println();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}