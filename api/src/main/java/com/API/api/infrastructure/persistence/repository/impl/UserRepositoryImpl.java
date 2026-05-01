package com.API.api.infrastructure.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.API.api.domain.model.User;
import com.API.api.domain.repository.UserRepository;
import com.API.api.infrastructure.persistence.entity.UserEntity;
import com.API.api.infrastructure.persistence.mapper.UserMapper;
import com.API.api.infrastructure.persistence.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jUserRepository;

    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entity = jUserRepository.findByEmail(email);
        return entity.map(UserMapper::toDomain);
    }

    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> entity = jUserRepository.findByUsername(username);
        return entity.map(UserMapper::toDomain);
    }

    public Optional<User> findByEmailOrUsername(String identity) {
        Optional<UserEntity> entity = jUserRepository.findByEmailOrUsername(identity, identity);
        return entity.map(UserMapper::toDomain);
    }

    public boolean existsByEmail(String email) {
        boolean entity = jUserRepository.existsByEmail(email);
        return entity;
    }

    public boolean existsByUsername(String username) {
        boolean entity = jUserRepository.existsByUsername(username);
        return entity;
    }

    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jUserRepository.save(entity);
        return UserMapper.toDomain(saved);
    }
}
