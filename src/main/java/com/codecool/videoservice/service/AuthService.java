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

    public Map<Object, Object> authenticate(VideoAppUser user) {

        String username = user.getEmail();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtTokenServices.createToken(username, roles);

        return createModel(username, token, roles);
    }

    private Map<Object, Object> createModel(String user, String token, List<String> roles){
        Map<Object, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("token", token);
        model.put("roles", roles);
        return model;
    }
}
