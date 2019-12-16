package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.VideoAppUser;
import com.codecool.videoservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody VideoAppUser user) {
        if (authService.isEmailExists(user.getEmail()))
            return new ResponseEntity<>("Already exists!", HttpStatus.CONFLICT);
        authService.saveUser(user);
        return ResponseEntity.ok("User saved successfully!");
    }

    @PostMapping("sign-in")
    public ResponseEntity signIn(@RequestBody VideoAppUser user) {
        Map<Object, Object> model = authService.authenticate(user);
        return ResponseEntity.ok(model);
    }
}
