package com.booktrackr.service;

import com.booktrackr.model.User;
import com.booktrackr.model.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * Responsible for loading user-specific data from the database during authentication.
 * This service integrates the application's {@link User} entity with Spring Security's
 * authentication process.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    /**
     * Loads a user from the database by their username.
     * If the user exists, it returns a Spring Security {@link UserDetails} object
     * containing the username, password hash, and granted authorities (empty in this case).
     * If the user does not exist, it throws a {@link UsernameNotFoundException}.
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
