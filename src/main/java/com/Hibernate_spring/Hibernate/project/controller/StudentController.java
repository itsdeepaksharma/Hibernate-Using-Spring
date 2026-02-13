package com.Hibernate_spring.Hibernate.project.controller;

import com.Hibernate_spring.Hibernate.project.entity.Student;
import com.Hibernate_spring.Hibernate.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    // Create a new student
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            // Validate input
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                return createErrorResponse("Student name is required", HttpStatus.BAD_REQUEST);
            }
            if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
                return createErrorResponse("Student email is required", HttpStatus.BAD_REQUEST);
            }

            studentDao.save(student);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Student created successfully");
            response.put("data", student);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Operation", "CREATE");
            headers.add("X-Resource-ID", student.getId().toString());
            
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
            
        } catch (Exception e) {
            return createErrorResponse("Failed to create student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all students
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentDao.findAll();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Students retrieved successfully");
            response.put("count", students.size());
            response.put("data", students);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Operation", "READ_ALL");
            headers.add("X-Total-Count", String.valueOf(students.size()));
            
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve students: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return createErrorResponse("Valid student ID is required", HttpStatus.BAD_REQUEST);
            }

            Student student = studentDao.findById(id);
            
            if (student != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Student retrieved successfully");
                response.put("data", student);
                
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Operation", "READ_BY_ID");
                headers.add("X-Resource-ID", id.toString());
                
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            } else {
                return createErrorResponse("Student not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            return createErrorResponse("Failed to retrieve student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            if (id == null || id <= 0) {
                return createErrorResponse("Valid student ID is required", HttpStatus.BAD_REQUEST);
            }
            
            // Validate input
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                return createErrorResponse("Student name is required", HttpStatus.BAD_REQUEST);
            }
            if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
                return createErrorResponse("Student email is required", HttpStatus.BAD_REQUEST);
            }

            Student existingStudent = studentDao.findById(id);
            if (existingStudent != null) {
                student.setId(id);
                studentDao.update(student);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Student updated successfully");
                response.put("data", student);
                
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Operation", "UPDATE");
                headers.add("X-Resource-ID", id.toString());
                
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            } else {
                return createErrorResponse("Student not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            return createErrorResponse("Failed to update student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return createErrorResponse("Valid student ID is required", HttpStatus.BAD_REQUEST);
            }

            Student existingStudent = studentDao.findById(id);
            if (existingStudent != null) {
                studentDao.delete(id);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Student deleted successfully");
                response.put("data", Map.of("id", id, "name", existingStudent.getName()));
                
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Operation", "DELETE");
                headers.add("X-Resource-ID", id.toString());
                
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            } else {
                return createErrorResponse("Student not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            return createErrorResponse("Failed to delete student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to create error responses
    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", message);
        errorResponse.put("status", status.value());
        errorResponse.put("timestamp", System.currentTimeMillis());
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error", "true");
        headers.add("X-Error-Code", String.valueOf(status.value()));
        
        return new ResponseEntity<>(errorResponse, headers, status);











//"Hey this is feature branch code"







    }
}
