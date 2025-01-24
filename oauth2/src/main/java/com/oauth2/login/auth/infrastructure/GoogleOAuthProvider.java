package com.oauth2.login.auth.infrastructure;

import com.oauth2.login.user.domain.request.UserCreateOrLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.oauth2.login.common.utils.StringUtils.encodeString;

@Component
@Slf4j
public class GoogleOAuthProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    private static final String scope = encodeString("openid email profile");

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.google.token-uri}")
    private String tokenUri;

    @Value("${oauth2.google.resource-uri}")
    private String resourceUri;

    public UserCreateOrLoginRequest getUserInfoByGoogle(String code) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        String accessToken = (String) responseBody.get("access_token");

        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        String userInfoUri = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, userInfoRequest, Map.class);

        Map<String, Object> userInfo = userInfoResponse.getBody();
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String nickname = RandomStringUtils.randomAlphanumeric(20);
        String picture = (String) userInfo.get("picture");

        return new UserCreateOrLoginRequest(email, name, nickname, picture);
    }

}
