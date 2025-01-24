package com.oauth2.login.auth.controller;

import com.oauth2.login.common.utils.StringUtils;
import com.oauth2.login.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static com.oauth2.login.common.utils.StringUtils.encodeString;

@RestController
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

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

    private final UserService userService;

    @GetMapping
    public void loginWithGoogle(HttpServletResponse response) throws IOException {
        String redirectUri = encodeString(this.redirectUri);
        String googleAuthUrl = StringUtils.format(
                "https://accounts.google.com/o/oauth2/v2/auth?client_id={}&redirect_uri={}&response_type=code&scope={}",
                        clientId, redirectUri, scope
        );

        response.sendRedirect(googleAuthUrl);
    }

    @GetMapping("/code/google")
    public ResponseEntity<String> handleGoogleCallback(@RequestParam("code") String code) {

        // Access Token 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            // Step 1: Access Token 요청
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
            Map<String, Object> responseBody = response.getBody();

            // Access Token 추출
            String accessToken = (String) responseBody.get("access_token");
            String idToken = (String) responseBody.get("id_token");

            // Step 2: UserInfo API로 이메일 정보 요청
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken); // Access Token을 Authorization 헤더에 추가
            HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);

            String userInfoUri = "https://www.googleapis.com/oauth2/v2/userinfo";
            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, userInfoRequest, Map.class);

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");
            String picture = (String) userInfo.get("picture");

            return ResponseEntity.ok("Email: " + email + ", Name: " + name);

        } catch (Exception e) {
            log.error("Failed to exchange authorization code for access token or fetch user info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to authenticate with Google.");
        }
    }
}
