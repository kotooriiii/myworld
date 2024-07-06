package com.github.kotooriiii.myworld.service.security.oidc.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthProviderFactory  {
    private final GoogleOAuthProvider googleOAuthProvider;
    private final AppleOAuthProvider appleOAuthProvider;
    private final MicrosoftOAuthProvider microsoftOAuthProvider;

    public OAuthProvider getProvider(String providerName) {
        return switch (providerName)
        {
            case "google" -> googleOAuthProvider;
            case "microsoft" -> microsoftOAuthProvider;
            case "apple" -> appleOAuthProvider;
            default -> null;
        };
    }
}