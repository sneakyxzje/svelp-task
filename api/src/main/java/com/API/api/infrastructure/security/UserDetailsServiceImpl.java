package com.API.api.infrastructure.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.API.api.domain.model.User;
import com.API.api.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String idOrUsername) {
        User user = userRepository.findById(Long.parseLong(idOrUsername))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idOrUsername));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf("ROLE_" + user.getRoles()));
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), List.of(authority));
    }
}
