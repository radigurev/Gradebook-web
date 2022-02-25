package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeBookUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public GradeBookUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User  user=userRepository.findByEmail(username).orElseThrow(() ->  new UsernameNotFoundException("User with "+username+" does not exist"));

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {

        List<GrantedAuthority> authorities= Arrays.stream(AccountType.values())
                 .map(r->new SimpleGrantedAuthority("ROLE_"+r.name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );  
    }
}
