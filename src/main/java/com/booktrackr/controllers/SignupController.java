package com.booktrackr.controllers;

import com.booktrackr.model.User;
import com.booktrackr.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles the signup process.
 * - Takes the user's username and password and the re-types for them
 * - Checks to make sure they're the same
 * - Encrypts the password using BCrypt Hashing
 * - Adds to repository
 */
@Controller
public class SignupController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String reUsername,
                         @RequestParam String password,
                         @RequestParam String rePassword,
                         Model model) {
        if (!username.equals(reUsername) || !password.equals(rePassword)) { // Check
            model.addAttribute("error", "Usernames or passwords do not match.");
            return "signup";
        }

        String encodedPassword = passwordEncoder.encode(password); // Encode the password
        User user = new User(username, encodedPassword); // Save new user
        userRepository.save(user);

        return "redirect:/";
    }
}
