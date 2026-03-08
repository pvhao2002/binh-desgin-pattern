package com.db.service.controller;

import com.db.service.dto.response.StudentOverviewResponse;
import com.db.service.facade.StudentManagementFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-overview")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentOverviewController {

    private final StudentManagementFacade facade;

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentOverviewResponse> getOverview(@PathVariable Long studentId) {
        return ResponseEntity.ok(facade.getStudentOverview(studentId));
    }
}
