package com.booktrackr.controllers;

import com.booktrackr.model.Textbook;
import com.booktrackr.model.TextbookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles removing a book from the repository.
 * - Only a single book can be removed at a time
 */
@Controller
public class RemoveController {
    private final TextbookRepository textbookRepository;
    public RemoveController(TextbookRepository textbookRepository) {
        this.textbookRepository = textbookRepository;
    }

    /**
     * Finds the book by its ID, then deletes it.
     */
    @PostMapping("/removeBookByID")
    public String removeBook(@RequestParam Long id, Model model) {
        if (textbookRepository.existsById(id)) {
            textbookRepository.deleteById(id);
            model.addAttribute("success", true);
        }
        else {
            model.addAttribute("error", "Book not found.");
        }
        return "redirect:/stock";
    }


    @GetMapping("/removeTextbooks")
    @ResponseBody
    public List<String> getTextbooks(@RequestParam String course, @RequestParam String teacher, @RequestParam(defaultValue = "") String q) {
        return textbookRepository.findByCourseAndTeacherIgnoreCaseAndTextbookIDContainingIgnoreCase(course, teacher, q).stream().map(Textbook::getTextbookID).toList();
    }
}