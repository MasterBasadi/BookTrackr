package com.booktrackr.controllers;

import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String Logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
