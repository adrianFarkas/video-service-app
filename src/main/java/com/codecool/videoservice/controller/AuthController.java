package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody VideoAppUser user) {
        String result = authService.saveUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody VideoAppUser user, HttpServletResponse response) {
        String res = authService.authenticate(user, response);
        return ResponseEntity.ok(res);
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

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
      String verified = authService.verifyAccount(token);
      return ResponseEntity.ok(verified);
    }
}
