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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implements the OAuth provider for Google.
 */
@Component
@RequiredArgsConstructor
public class GoogleOAuthProvider implements OAuthProvider
{

    @Value("${oidc.providers.google.client-id}")
    private String clientId;

    @Value("${oidc.providers.google.client-secret}")
    private String clientSecret;

    @Value("${oidc.providers.google.token-uri}")
    private String tokenUri;

    @Value("${oidc.providers.google.peoples-uri}")
    private String peopleApiUrl;

    @Value("${oidc.providers.google.jwk-set-uri}")
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
        params.add("client_secret", clientSecret);
        params.add("code_verifier", codeVerifier);


        UriComponents build = UriComponentsBuilder.fromUriString(tokenUri)
                .queryParams(params)
                .build();


        ResponseEntity<Map> response = restClient.post()
                .uri(build.toString())
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
            String imageUrl = jwt.getClaimAsString("picture");


            // Use helper methods to extract complex claims from access token
            Map stringStringMap = fetchGoogleUserInfo(accessToken);
            String birthDate = extractBirthDate(stringStringMap);
            String gender = extractGender(stringStringMap);

            // Create an AuthorRegistrationRequest object to store user information for registration or update
            AuthorRegistrationRequest authorRegistrationRequest =
                    new AuthorRegistrationRequest(
                            name,
                            email,
                            UUID.randomUUID().toString(), // Generate a new random UUID for the user
                            DateUtils.parseToLocalDate(birthDate), // Parse birthdate string to LocalDate, may be null
                            gender == null ? null : Gender.valueOf(gender.toUpperCase()), // Convert gender string to enum, may be null
                            null // Use the image URL directly
                    );

            // Register or update the user in the system using the AuthorService
            AuthorDTO authorDTO = authorService.registerOrCreateAuthor(authorRegistrationRequest);

            if (imageUrl != null)
            {
                ImageUtils.ImageFile imageFile = ImageUtils.createImageFile(imageUrl);
                authorService.uploadAuthorProfileImage(authorDTO.id(), imageFile.data(), imageFile.fileExtension());
            }


            // Generate a new access token for the authenticated user
            String token = jwtUtil.issueToken(authorDTO.username(), authorDTO.roles());

            // Return the AuthenticationResponse with the access token and user information
            return new AuthenticationResponse(token, authorDTO);
        } catch (Exception e)
        {
            throw new OAuthAuthenticationException("Error occurred during OAuth2 authentication: " + e.getMessage());
        }
    }

    private Map fetchGoogleUserInfo(String accessToken)
            throws OAuthAuthenticationException
    {

        RestClient restClient = RestClient.create();

        // Prepare the query parameters for the People API call
        UriComponents peopleApiUri = UriComponentsBuilder.fromHttpUrl(peopleApiUrl)
                .queryParam("personFields", "genders,birthdays")
                .build();

        // Use the access_token as Bearer token for Authorization
        ResponseEntity<Map> peopleApiResponse = restClient.get()
                .uri(peopleApiUri.toUri())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .toEntity(Map.class);

        if (!peopleApiResponse.getStatusCode().is2xxSuccessful())
        {
            throw new OAuthAuthenticationException("Failed to fetch user information from Google People API"); //todo this and the next throw same exception
        }

        Map userInfo = peopleApiResponse.getBody();
        if (userInfo == null)
        {
            throw new OAuthAuthenticationException("Failed to fetch user information from Google People API");

        }

        return userInfo;


    }

    /**
     * Helper method to extract the birthday from the user profile information
     *
     * @param userProfileBody the map containing user profile information
     * @return the birthday in "YYYY-MM-DD" format or null if not found
     */
    private String extractBirthDate(Map userProfileBody)
    {
        if (userProfileBody.containsKey("birthdays") && !((List<Map<String, Object>>) userProfileBody.get("birthdays")).isEmpty())
        {
            List<Map<String, Object>> birthdays = (List<Map<String, Object>>) userProfileBody.get("birthdays");
            birthdays.sort((o1, o2) -> Integer.compare(((Map<String, Object>) o2.get("date")).size(), ((Map<String, Object>) o1.get("date")).size()));
            Map<String, Object> firstBirthday = birthdays.get(0); // Get the first birthday entry

            // Check if the 'date' claim is present in the first birthday
            if (firstBirthday.containsKey("date"))
            {
                Map<String, Object> date = (Map<String, Object>) firstBirthday.get("date");

                Object yearObject = date.get("year");

                // Construct the birthday string in the format "YYYY-MM-DD", if year is not present then, current year will be used.
                return String.format("%d-%d-%d", yearObject == null ? LocalDateTime.now().getYear() : yearObject, date.get("month"), date.get("day"));
            }
        }
        return null; // Birthday not found, return null
    }

    /**
     * Helper method to extract the gender from the user profile information
     *
     * @param userProfileBody the map containing user profile information.
     * @return the gender or null if not found
     */
    private String extractGender(Map userProfileBody)
    {
        if (userProfileBody.containsKey("genders") && !((List<Map<String, Object>>) userProfileBody.get("genders")).isEmpty())
        {
            List<Map<String, Object>> genders = (List<Map<String, Object>>) userProfileBody.get("genders");
            Map<String, Object> gender = genders.get(0); // Get the first gender entry

            if (gender.containsKey("value"))
            {
                return (String) gender.get("value");
            }
        }
        return null; // Gender not found, return null
    }
}