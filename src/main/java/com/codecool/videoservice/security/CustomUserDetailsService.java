package com.codecool.videoservice.security;

import com.codecool.videoservice.model.user.CustomUserDetails;
import com.codecool.videoservice.model.user.VideoAppUser;
import com.codecool.videoservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        VideoAppUser user = users.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User email: " + userEmail + " not found"));

        return CustomUserDetails.create(user);
    }
}

