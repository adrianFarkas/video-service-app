package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody VideoAppUser user) {
        authService.saveUser(user);
        return ResponseEntity.ok("User saved successfully!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody VideoAppUser user, HttpServletResponse response) {
        try {
            authService.authenticate(user, response);
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/logout")
    public ResponseEntity signIn(HttpServletResponse response) {
        authService.logout(response);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/exists")
    public ResponseEntity checkEmailExists(@RequestParam String email) {
        if (authService.isEmailExists(email))
            return ResponseEntity.ok(true);
        return  ResponseEntity.ok(false);
    }
}
