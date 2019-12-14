package com.codecool.videoservice.service;

import com.codecool.videoservice.model.VideoAppUser;
import com.codecool.videoservice.repository.UserRepository;
import com.codecool.videoservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void saveUser(VideoAppUser user) {
        user.setRoles(Collections.singletonList("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Map<Object, Object> createModel(VideoAppUser user) {
        Map<Object, Object> model = new HashMap<>();
        String token = createToken(user);
        model.put("user", user.getEmail());
        model.put("token", token);
        model.put("roles", user.getRoles());
        return model;
    }

    private String createToken(VideoAppUser user){
        String username = user.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        return jwtTokenServices.createToken(username, roles);
    }
}
