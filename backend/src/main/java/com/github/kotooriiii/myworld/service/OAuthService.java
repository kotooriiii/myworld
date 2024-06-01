package com.github.kotooriiii.myworld.service;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import com.github.kotooriiii.myworld.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService
{
    private final AuthorService authorService;
    private final JWTUtil jwtUtil;
    @Value("${spring.security.oauth2.client.provider.google.jwk-set-uri}")
    private String jwkSetUri;
    public AuthenticationResponse loginByOAuth2(String idToken)
    {

        try {
            // Decode and verify the ID token
            JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
            Jwt jwt = jwtDecoder.decode(idToken);

            // Extract user information from the ID token
            String subject = jwt.getClaimAsString("sub");
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");
            String birthDate = jwt.getClaimAsString("birth_date");
            String gender = jwt.getClaimAsString("gender");
            String image = jwt.getClaimAsString("image"); //pfp todo



            AuthorRegistrationRequest authorRegistrationRequest =
                    new AuthorRegistrationRequest(
                            name,
                            email,
                            UUID.randomUUID().toString(),
                            birthDate == null ? null : DateUtils.parseToLocalDate(birthDate),
                            gender == null ? null : Gender.valueOf(gender.toUpperCase()),
                            image);

            AuthorDTO authorDTO = authorService.registerOrCreateAuthor(authorRegistrationRequest);

            String token = jwtUtil.issueToken(authorDTO.username(), authorDTO.roles());

            return new AuthenticationResponse(token, authorDTO);
        } catch (Exception e) {
            throw e;
        }





    }
}
