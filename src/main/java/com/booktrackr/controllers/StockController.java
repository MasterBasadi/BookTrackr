package com.booktrackr.controllers;

import com.booktrackr.config.SecurityConfig;
import com.booktrackr.model.Textbook;
import com.booktrackr.model.TextbookRepository;
import com.booktrackr.model.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsible for displaying and exporting the user's textbook stock.
 * - Stock view with counts and overdue tracking
 * - CSV export of all textbooks for the logged-in user
 */
@Controller
public class StockController {
    @Autowired
    private SecurityConfig securityConfig;
    private final TextbookRepository textbookRepository;

    public StockController(TextbookRepository textbookRepository) {
        this.textbookRepository = textbookRepository;
    }

    /**
     * Displays the stock page for the logged-in user.
     * - Filters textbooks owned by the current user
     * - Calculates assigned, unassigned, and overdue counts
     * - Passes all data to the view for rendering
     */
    @GetMapping("/stock")
    public String showStock(HttpSession session, Model model) {
        User user = securityConfig.getLoggedInUser(); // Check
        if (user == null) {
            return "redirect:/";
        }

        List<Textbook> userBooks = textbookRepository.findAll().stream() // Filter only this user's books
                .filter(book -> book.getUser() != null && book.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        long assignedCount = userBooks.stream()
                .filter(book -> book.getStudentName() != null && !book.getStudentName().trim().isEmpty())
                .count();

        long unassignedCount = userBooks.size() - assignedCount;

        long overdueCount = userBooks.stream()
                .filter(book -> book.getReturnDate() != null && book.getReturnDate().isBefore(LocalDate.now()))
                .count();

        model.addAttribute("books", userBooks);
        model.addAttribute("totalBooks", userBooks.size());
        model.addAttribute("assignedCount", assignedCount);
        model.addAttribute("unassignedCount", unassignedCount);
        model.addAttribute("overdueCount", overdueCount);

        return "stock";
    }

    /**
     * Exports the logged-in user's textbooks as a CSV file.
     * - Validates the user is logged in
     * - Generates a CSV with textbook details
     * - Sends file as a downloadable attachment
     */
    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response, HttpSession session) throws IOException {
        User user = securityConfig.getLoggedInUser(); // User
        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        response.setContentType("text/csv"); // Make file
        response.setHeader("Content-Disposition", "attachment; filename=\"textbook_stock.csv\"");

        List<Textbook> books = textbookRepository.findByUser(user); // Get data

        PrintWriter writer = response.getWriter();
        writer.println("Textbook ID,Course,Condition,Teacher,Assigned To,Return Date");

        for (Textbook book : books) { // Writing into csv
            String line = String.format(
                    "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    book.getTextbookID(),
                    book.getCourse(),
                    book.getCondition(),
                    book.getTeacher(),
                    book.getStudentName() != null ? book.getStudentName() : "",
                    book.getReturnDate() != null ? book.getReturnDate().toString() : ""
            );
            writer.println(line);
        }

        writer.flush(); // Clean up writer
        writer.close();
    }
}