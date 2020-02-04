package com.codecool.videoservice.controller;

import com.codecool.videoservice.model.user.BasicUserInformation;
import com.codecool.videoservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public BasicUserInformation getUserData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getBasicInformationByEmail(auth.getName());
    }

    @GetMapping("/{id}")
    public BasicUserInformation getUserData(@PathVariable String id) {
        return userRepository.getBasicInformationById(id);
    }
}
