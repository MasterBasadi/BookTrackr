package com.booktrackr.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long> {
    List<Textbook> findByUser(User user);
    Textbook findByCourseAndTextbookIDAndTeacher(String course, String textbookID, String teacher);
    List<Textbook> findByCourseAndTeacherIgnoreCaseAndTextbookIDContainingIgnoreCase(String course, String teacher, String textbookIDFragment);
}
