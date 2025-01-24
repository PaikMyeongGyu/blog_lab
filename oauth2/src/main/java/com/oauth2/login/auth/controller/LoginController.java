package com.oauth2.login.auth.controller;

import com.oauth2.login.common.utils.StringUtils;
import com.oauth2.login.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.FOUND;

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


    private static String encodeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }
}
