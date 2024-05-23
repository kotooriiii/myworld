package com.github.kotooriiii.myworld.dto.request.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
