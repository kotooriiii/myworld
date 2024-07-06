package com.github.kotooriiii.myworld.service.security.oidc.auth;

import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;

/**
 * Defines the contract for an OAuth provider.
 */
public interface OAuthProvider {
    /**
     * Authenticates a user using the OAuth code grant flow.
     *
     * @param authorizationCode The authorization code.
     * @param codeVerifier The code verifier.
     * @return The authentication response.
     */
    AuthenticationResponse authenticate(String authorizationCode, String codeVerifier)
            throws OAuthAuthenticationException;
}