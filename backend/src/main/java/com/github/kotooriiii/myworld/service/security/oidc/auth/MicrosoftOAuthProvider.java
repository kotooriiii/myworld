package com.github.kotooriiii.myworld.service.security.oidc.auth;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.dto.request.author.AuthorRegistrationRequest;
import com.github.kotooriiii.myworld.dto.response.auth.AuthenticationResponse;
import com.github.kotooriiii.myworld.enums.Gender;
import com.github.kotooriiii.myworld.service.AuthorService;
import com.github.kotooriiii.myworld.service.security.jwt.JWTUtil;
import com.github.kotooriiii.myworld.util.DateUtils;
import com.github.kotooriiii.myworld.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implements the OAuth provider for Microsoft.
 */
@Component
@RequiredArgsConstructor
public class MicrosoftOAuthProvider implements OAuthProvider
{
    @Value("${oidc.providers.microsoft.client-id}")
    private String clientId;

    @Value("${oidc.providers.microsoft.client-secret}")
    private String clientSecret;

    @Value("${oidc.providers.microsoft.token-uri}")
    private String tokenUri;

    @Value("${oidc.providers.microsoft.profile-uri}")
    private String profileUri;
    @Value("${oidc.providers.microsoft.photo-uri}")
    private String photoUri;

    @Value("${oidc.providers.microsoft.jwk-set-uri}")
    private String jwkSetUri;

    private final AuthorService authorService;
    private final JWTUtil jwtUtil;

    @Override
    public AuthenticationResponse authenticate(String authorizationCode, String codeVerifier)
            throws OAuthAuthenticationException
    {
        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", authorizationCode);
        params.add("redirect_uri", "https://localhost/callback");
        params.add("client_id", clientId);
        //params.add("client_secret", clientSecret);
        params.add("code_verifier", codeVerifier);


        UriComponents build = UriComponentsBuilder.fromUriString(tokenUri)
                .build();

        ResponseEntity<Map> response = restClient.post()
                .uri(build.toString())
                .header("Origin", "https://localhost")
                .body(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .toEntity(Map.class);

        Map responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("id_token") && responseBody.containsKey("access_token"))
        {
            return loginByOAuth2((String) responseBody.get("id_token"), (String) responseBody.get("access_token"));
        } else
        {
            throw new OAuthAuthenticationException("Authentication failed");
        }
    }

    /**
     * Method to authenticate a user using OAuth2 and retrieve their information
     *
     * @param idToken the ID token received from the OAuth2 provider
     * @return an AuthenticationResponse containing the access token and user information
     * @throws Exception if an error occurs during authentication
     */
    public AuthenticationResponse loginByOAuth2(String idToken, String accessToken)
            throws OAuthAuthenticationException
    {

        try
        {
            // Create a JwtDecoder using the Nimbus library to decode and verify the ID token
            JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
            Jwt jwt = jwtDecoder.decode(idToken);

            // Extract claims from the ID token
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");

            // Use helper methods to extract complex claims from access token
            ImageUtils.ImageFile imageFile = fetchPhoto(accessToken);
            String birthDate = fetchBirthDate(accessToken);
            String gender = null; //microsoft does not contain

            // Create an AuthorRegistrationRequest object to store user information for registration or update
            AuthorRegistrationRequest authorRegistrationRequest =
                    new AuthorRegistrationRequest(
                            name,
                            email,
                            UUID.randomUUID().toString(), // Generate a new random UUID for the user
                            DateUtils.parseToLocalDate(birthDate), // Parse birthdate string to LocalDate, may be null
                            gender == null ? null : Gender.valueOf(gender.toUpperCase()), // Convert gender string to enum, may be null
                            null // Use the image URL directly todo object storagelink
                    );

            // Register or update the user in the system using the AuthorService
            AuthorDTO authorDTO = authorService.registerOrCreateAuthor(authorRegistrationRequest);
            if (imageFile != null)
                authorService.uploadAuthorProfileImage(authorDTO.id(), imageFile.data(), imageFile.fileExtension());

            // Generate a new access token for the authenticated user
            String token = jwtUtil.issueToken(authorDTO.username(), authorDTO.roles());

            // Return the AuthenticationResponse with the access token and user information
            return new AuthenticationResponse(token, authorDTO);
        } catch (Exception e)
        {
            throw new OAuthAuthenticationException("Error occurred during OAuth2 authentication: " + e.getMessage());
        }
    }

    private String fetchBirthDate(String accessToken)
            throws OAuthAuthenticationException
    {

        RestClient restClient = RestClient.create();

        // Prepare the query parameters for the People API call
        UriComponents profileUri = UriComponentsBuilder.fromHttpUrl(this.profileUri)
                .build();

        // Use the access_token as Bearer token for Authorization
        ResponseEntity<Map> profileUriResponse = restClient.get()
                .uri(profileUri.toUri())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .toEntity(Map.class);

        Map userInfo = profileUriResponse.getBody();

        List<Map<String, Object>> anniversaries;
        if (userInfo != null && (anniversaries = (List<Map<String, Object>>) userInfo.get("anniversaries")) != null)
        {
            Optional<Map<String, Object>> first = anniversaries.stream().filter(item -> item.get("type").equals("birthday")).findFirst();

            return first.map(map -> (String) map.get("date")).orElse(null);
        } else
        {
            throw new OAuthAuthenticationException("Failed to fetch user information from Google People API");
        }
    }

    private ImageUtils.ImageFile fetchPhoto(String accessToken)
            throws OAuthAuthenticationException
    {

        RestClient restClient = RestClient.create();

        // Prepare the query parameters for the People API call
        UriComponents photoUri = UriComponentsBuilder.fromHttpUrl(this.photoUri)
                .build();

        // Use the access_token as Bearer token for Authorization
        ResponseEntity<byte[]> photoUriResponse = restClient.get()
                .uri(photoUri.toUri())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .toEntity(byte[].class);
        MediaType mediaType = photoUriResponse.getHeaders().getContentType();

        if(photoUriResponse.getBody() == null || mediaType == null)
        {
            return null;
        }
        return new ImageUtils.ImageFile(photoUriResponse.getBody(), mediaType.toString());
    }
}