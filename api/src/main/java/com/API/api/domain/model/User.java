package com.API.api.domain.model;

import com.API.api.domain.enums.Role;

public class User {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private Role role;

    public User(Long id, String fullname, String username, String email, String password, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRoles() {
        return this.role;
    }
}
