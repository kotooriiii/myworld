package com.github.kotooriiii.myworld.controller;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.service.AuthorService;
import com.github.kotooriiii.myworld.service.OAuthService;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import com.github.kotooriiii.myworld.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class OAuthController
{
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    private final OAuthService oAuthService;
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> body) {
        String authorizationCode = body.get("authorizationCode");
        String code_verifier = body.get("code_verifier");

        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); //todo shouldnt we get the ID token instead?
        params.add("code", authorizationCode);
        params.add("redirect_uri", "https://localhost/callback");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code_verifier", code_verifier);


        UriComponents build = UriComponentsBuilder.fromUriString(tokenUri)
                .queryParams(params)
                .build();


        ResponseEntity<Map> response = restClient.post()
                .uri(build.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .toEntity(Map.class);

        Map responseBody = response.getBody();


        if (responseBody != null && responseBody.containsKey("id_token")) {
            AuthenticationResponse authenticationResponse = oAuthService.loginByOAuth2((String) responseBody.get("id_token"));

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, authenticationResponse.token())
                    .body(authenticationResponse.authorDTO());
        } else {
            return ResponseEntity.badRequest().body("Authentication failed");
        }
    }


}