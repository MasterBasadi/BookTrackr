package com.booktrackr.controllers;

import com.booktrackr.config.SecurityConfig;
import com.booktrackr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/login")
    public String login() {
        return "redirect:/home";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        User loggedInUser = securityConfig.getLoggedInUser();
        return "redirect:/home";
    }
}
