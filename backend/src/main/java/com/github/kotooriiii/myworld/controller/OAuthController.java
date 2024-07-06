package com.github.kotooriiii.myworld.controller;

import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.service.security.oidc.OAuthService;
import com.github.kotooriiii.myworld.service.security.oidc.auth.OAuthAuthenticationException;
import com.github.kotooriiii.myworld.service.security.oidc.auth.UnsupportedProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class OAuthController
{
    private final OAuthService oAuthService;

    /**
     * Authenticates a user using the provided authorization code and code verifier.
     *
     * @param body The request body containing the authorization code, code verifier, and provider name.
     * @return The authentication response.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> body) {
        // Check if the request body is not empty
        if (body == null || body.isEmpty()) {
            return ResponseEntity.badRequest().body("Request body is empty");
        }

        // Extract required parameters from the request body
        String authorizationCode = body.get("authorizationCode");
        String codeVerifier = body.get("code_verifier");
        String providerName = body.get("oauth_provider");

        // Check if required parameters are present in the request body
        if (authorizationCode == null || codeVerifier == null || providerName == null) {
            return ResponseEntity.badRequest().body("Missing required parameters");
        }

        try {
            AuthenticationResponse authenticationResponse = oAuthService.authenticate(authorizationCode, codeVerifier, providerName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, authenticationResponse.token())
                    .body(authenticationResponse.authorDTO());
        } catch (UnsupportedProviderException | OAuthAuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}