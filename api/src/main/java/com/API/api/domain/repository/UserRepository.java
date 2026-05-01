package com.API.api.domain.repository;

import java.util.Optional;

import com.API.api.domain.model.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String identity);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
