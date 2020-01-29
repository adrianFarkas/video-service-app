package com.codecool.videoservice.service;

import com.codecool.videoservice.model.ConfirmationToken;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.ConfirmationTokenRepository;
import com.codecool.videoservice.repository.UserRepository;
import com.codecool.videoservice.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.codecool.videoservice.util.ResponseMsg.*;

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

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${jwt.validity}")
    private int validityInMs;

    public String saveUser(VideoAppUser user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setRoles(Collections.singletonList("ROLE_USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            userRepository.save(user);
            sendConfirmationEmail(user, createConfirmationToken(user));
            return SUCCESS.getMsg();
        }
        return UNSUCCESSFUL.getMsg();
    }

    public VideoAppUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).orElse(null);
    }

    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String authenticate(VideoAppUser user, HttpServletResponse response) {
        try {
            String username = user.getEmail();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(authentication.getName(), roles);
            response.addCookie(getUserCookie(token, validityInMs / 1000));
        } catch (DisabledException e) {
            return DISABLED.getMsg();
        } catch (AuthenticationException e) {
            return INCORRECT.getMsg();
        }
        return SUCCESS.getMsg();
    }

    public void logout(HttpServletResponse response) {
        response.addCookie(getUserCookie(null, 0));
    }

    public String verifyAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByConfirmationToken(token);
        if (confirmationToken != null) {
            Optional<VideoAppUser> user = userRepository.findByEmail(confirmationToken.getVideoAppUser().getEmail());
            user.ifPresent(videoAppUser -> {
                videoAppUser.setEnabled(true);
                userRepository.save(videoAppUser);
            });
            confirmationTokenRepository.deleteByConfirmationToken(token);
            return SUCCESS.getMsg();
        }
        return UNSUCCESSFUL.getMsg();
    }

    private Cookie getUserCookie(String value, Integer age) {
        Cookie cookie = new Cookie("u_token", value);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private ConfirmationToken createConfirmationToken(VideoAppUser user) {
        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenRepository.saveAndFlush(token);
        return token;
    }

    private void sendConfirmationEmail(VideoAppUser user, ConfirmationToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify your email!");
        mailMessage.setText("Dear " + user.getFirstName() + " " + user.getLastName() + ",\n" +
                "To confirm your account, please click here: \n" +
                "http://localhost:3000/verify?token=" + token.getConfirmationToken());

        emailService.sendEmail(mailMessage);
    }
}
