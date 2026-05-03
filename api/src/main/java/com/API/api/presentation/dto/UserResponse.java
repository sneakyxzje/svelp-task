package com.API.api.presentation.dto;

import java.util.List;

public record UserResponse(
        String username,
        List<String> roles) {

}
