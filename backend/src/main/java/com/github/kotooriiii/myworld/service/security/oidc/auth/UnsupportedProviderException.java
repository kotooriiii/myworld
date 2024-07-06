package com.github.kotooriiii.myworld.service.security.oidc.auth;

/**
 * Thrown when an unsupported provider is requested.
 */
public class UnsupportedProviderException extends Exception {
    public UnsupportedProviderException(String message) {
        super(message);
    }
}