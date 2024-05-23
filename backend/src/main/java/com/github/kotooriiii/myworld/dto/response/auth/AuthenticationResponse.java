package com.github.kotooriiii.myworld.dto.response.auth;

import com.github.kotooriiii.myworld.dto.AuthorDTO;

public record AuthenticationResponse (
        String token,
        AuthorDTO authorDTO){
}
