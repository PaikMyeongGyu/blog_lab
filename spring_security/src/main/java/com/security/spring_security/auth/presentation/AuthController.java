package com.security.spring_security.auth.presentation;

import com.security.spring_security.auth.annotation.ReIssue;
import com.security.spring_security.auth.annotation.User;
import com.security.spring_security.auth.dto.LoginUser;
import com.security.spring_security.auth.dto.request.LoginRequest;
import com.security.spring_security.auth.dto.response.TokenResponse;
import com.security.spring_security.auth.service.AuthService;
import com.security.spring_security.user.domain.Users;
import com.security.spring_security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.security.spring_security.auth.util.TokenUtils.REFRESH_TOKEN_EXPIRATION_TIME;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> userLogin(@RequestBody LoginRequest req) {
        Users findUser = userService.findUserByEmailAndPassword(req.email(), req.password());
        TokenResponse tokenResponse = authService.createTokenByUserInfo(findUser, REFRESH_TOKEN_EXPIRATION_TIME);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@ReIssue TokenResponse tokenResponse) {
        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> userLogout(@User LoginUser loginUser) {
        authService.deleteTokenByUserId(loginUser.userId());
        return ResponseEntity.noContent().build();
    }
}
