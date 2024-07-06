package com.github.kotooriiii.myworld.service.security.oidc.auth;

import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the OAuth provider for Apple.
 */
@Component
public class AppleOAuthProvider implements OAuthProvider {
    @Value("${oidc.providers.apple.client-id}")
    private String clientId;

    @Value("${oidc.providers.apple.client-secret}")
    private String clientSecret;

    @Value("${oidc.providers.apple.token-uri}")
    private String tokenUri;

    @Value("${oidc.providers.apple.peoples-uri}")
    private String peopleApiUrl;

    @Value("${oidc.providers.apple.jwk-set-uri}")
    private String jwkSetUri;

    @Override
    public AuthenticationResponse authenticate(String authorizationCode, String codeVerifier) {
        // Implement the authentication logic for Apple
        // ...
        return null;
    }
}