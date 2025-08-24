package com.booktrackr.controllers;

import com.booktrackr.config.SecurityConfig;
import com.booktrackr.model.Textbook;
import com.booktrackr.model.TextbookRepository;
import com.booktrackr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles the assignment of a book to a student
 * - Adds the student's name
 * - Sets the desired return date for the student
 */
@Controller
public class AssignController {
    @Autowired
    private SecurityConfig securityConfig;
    private final TextbookRepository textbookRepository;
    public AssignController(TextbookRepository textbookRepository) {
        this.textbookRepository = textbookRepository;
    }
    /**
     * Handles assigning an existing book
     * - Finds the books in the repository
     * - Assigns it a name and a return date
     */
    @PostMapping("/assignBook")
    public String assignBook(@RequestParam String course, @RequestParam String teacher, @RequestParam String textbookID, @RequestParam String studentName, @RequestParam String returnDate, Model model) { // Assigns the user-requested book in the database
        Textbook textbook = textbookRepository.findByCourseAndTextbookIDAndTeacher(course, textbookID, teacher); // Find the book first
        if (textbook != null) {
            textbook.setStudentName(studentName);
            textbook.setReturnDate(LocalDate.parse(returnDate)); // Parse String to LocalDate
            textbookRepository.save(textbook);
            System.out.println("Book Successfully Assigned ✅");
            model.addAttribute("success", true);
        }
        else {
            model.addAttribute("error", "Book not found.");
        }
        return "redirect:/stock";
    }
    /**
     * Clears the book's name and return date
     */
    @PostMapping("/returnBook/{id}")
    public String returnBook(@PathVariable Long id) {
        var optionalBook = textbookRepository.findById(id);

        if (optionalBook.isPresent()) {
            var book = optionalBook.get();

            User user = securityConfig.getLoggedInUser(); // Ensure this book belongs to the logged-in user
            if (book.getUser() != null && book.getUser().getId().equals(user.getId())) {
                book.setStudentName(null);
                book.setReturnDate(null);
                textbookRepository.save(book);
            }
        }

        return "redirect:/stock";
    }

    @GetMapping("/getTextbooks")
    @ResponseBody
    public List<String> getTextbooks(@RequestParam String course, @RequestParam String teacher, @RequestParam(defaultValue = "") String q) {
        return textbookRepository.findByCourseAndTeacherIgnoreCaseAndTextbookIDContainingIgnoreCase(course, teacher, q).stream().map(Textbook::getTextbookID).toList();
    }
}