package com.security.spring_security.user.presentation;

import com.security.spring_security.user.domain.dto.InactiveUserRequest;
import com.security.spring_security.user.domain.dto.JoinUserRequest;
import com.security.spring_security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> joinUser(@RequestBody JoinUserRequest req) {
        userService.joinUser(req.username(), req.email(), req.password());
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> inactiveUser(@RequestBody InactiveUserRequest req) {
        userService.inactivateUser(req.email(), req.password());
        return ResponseEntity.noContent().build();
    }
}
