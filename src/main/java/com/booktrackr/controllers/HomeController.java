package com.booktrackr.controllers;

import com.booktrackr.config.SecurityConfig;
import com.booktrackr.model.Textbook;
import com.booktrackr.model.TextbookRepository;
import com.booktrackr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles the homepage
 * - Dynamic statistics for the number of the books in the repository
 */
@Controller
public class HomeController {
    @Autowired
    private SecurityConfig securityConfig;
    private final TextbookRepository textbookRepository;

    public HomeController(TextbookRepository textbookRepository) {
        this.textbookRepository = textbookRepository;
    }

    @GetMapping("/")
    public String login() {
        return "login"; // renders login.html
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // renders signup.html
    }

    /**
     * Finds and sets the number of books that fit in each category
     */
    @GetMapping("/home")
    public String home(Model model) {
        User user = securityConfig.getLoggedInUser();
        if (user == null) {
            return "redirect:/";
        }

        List<Textbook> books = textbookRepository.findByUser(user);

        long total = books.size();
        long assigned = books.stream()
                .filter(book -> book.getStudentName() != null && !book.getStudentName().isBlank())
                .count();
        long overdue = books.stream()
                .filter(book -> book.getReturnDate() != null && book.getReturnDate().isBefore(LocalDate.now()))
                .count();

        model.addAttribute("totalBooks", total);
        model.addAttribute("assignedCount", assigned);
        model.addAttribute("overdueCount", overdue);

        return "home";
    }
}