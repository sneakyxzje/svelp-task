package com.API.api.infrastructure.persistence.mapper;

import com.API.api.domain.model.User;
import com.API.api.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles());
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setFullname(user.getFullname());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles());
        return entity;
    }
}
