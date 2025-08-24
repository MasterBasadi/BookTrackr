package com.booktrackr.controllers;

import com.booktrackr.config.SecurityConfig;
import com.booktrackr.model.Textbook;
import com.booktrackr.model.TextbookRepository;
import com.booktrackr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling textbook addition functionality.
 * Supports adding single textbooks or multiple textbooks in a batch.
 */
@Controller
public class AddController {
    @Autowired
    private SecurityConfig securityConfig;
    private final TextbookRepository textbookRepository;

    public AddController(TextbookRepository textbookRepository) {
        this.textbookRepository = textbookRepository;
    }

    /**
     * Handles adding a single textbook to the repository.
     * - Retrieves the logged-in user
     * - Creates a new textbook record with the provided details
     * - Sets student and return date as unassigned/null
     */
    @PostMapping("/addBook") // Single add method
    public String addBook(
            @RequestParam String course,
            @RequestParam String teacher,
            @RequestParam String condition,
            @RequestParam String textbookID
    ) {
        if (!textbookID.isEmpty()) {
            User user = securityConfig.getLoggedInUser();
            Textbook textbook = new Textbook(course, textbookID, condition, teacher);
            textbook.setStudentName(null); // explicitly unassigned
            textbook.setReturnDate(null); // no due date yet
            textbook.setUser(user);
            textbookRepository.save(textbook);
        }
        return "redirect:/stock";
    }

    /**
     * Handles adding multiple textbooks in a numeric sequence.
     * - Parses the starting ID format (e.g., "2025-01")
     * - Iterates from the lower to higher range, generating each textbook ID
     * - Creates a new textbook record for each ID
     */
    @PostMapping("/batchAdd") // Batch add method
    public String batchAdd(
            @RequestParam String course,
            @RequestParam String teacher,
            @RequestParam String condition,
            @RequestParam String lower,
            @RequestParam Integer higher
    ) {
        String[] lowerParts = lower.split("-");
        String year = lowerParts[0];
        int startNum = Integer.parseInt(lowerParts[1]);

        for (int i = startNum; i <= higher; i++) {
            String paddedNum = (i < 10 ? "0" : "") + i;
            String fullID = year + "-" + paddedNum;

            User user = securityConfig.getLoggedInUser();
            Textbook textbook = new Textbook(course, fullID, condition, teacher);
            textbook.setStudentName(null); // Explicitly unassigned
            textbook.setReturnDate(null); // No due date yet
            textbook.setUser(user);
            textbookRepository.save(textbook);
        }

        return "redirect:/stock";
    }
}