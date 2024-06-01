package com.github.kotooriiii.myworld.service;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.auth.AuthenticationRequest;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.model.Author;
import com.github.kotooriiii.myworld.service.mapper.AuthorMapper;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import com.github.kotooriiii.myworld.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AuthorMapper authorMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        Author principal = (Author) authentication.getPrincipal();
        AuthorDTO authorDTO = authorMapper.apply(principal);
        String token = jwtUtil.issueToken(authorDTO.username(), authorDTO.roles());
        return new AuthenticationResponse(token, authorDTO);
    }


}
