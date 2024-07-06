package com.github.kotooriiii.myworld.service.security.oidc;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.service.AuthorService;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import com.github.kotooriiii.myworld.service.security.oidc.auth.OAuthAuthenticationException;
import com.github.kotooriiii.myworld.service.security.oidc.auth.OAuthProvider;
import com.github.kotooriiii.myworld.service.security.oidc.auth.OAuthProviderFactory;
import com.github.kotooriiii.myworld.service.security.oidc.auth.UnsupportedProviderException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OAuthService
{
    private final OAuthProviderFactory oAuthProviderFactory;

    /**
     * Authenticates a user using the OAuth code grant flow.
     *
     * @param authorizationCode The authorization code.
     * @param codeVerifier The code verifier.
     * @param providerName The name of the provider.
     * @return The authentication response.
     * @throws UnsupportedProviderException If the provider is not supported.
     */
    public AuthenticationResponse authenticate(String authorizationCode, String codeVerifier, String providerName)
            throws UnsupportedProviderException, OAuthAuthenticationException
    {
        OAuthProvider provider = oAuthProviderFactory.getProvider(providerName);
        if (provider == null) {
            throw new UnsupportedProviderException("Unsupported provider");
        }

        return provider.authenticate(authorizationCode, codeVerifier);
    }
}
