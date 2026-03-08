package com.db.service.controller;

import com.db.service.dto.response.ScoreResponse;
import com.db.service.mediator.StudentRegistrationMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final StudentRegistrationMediator registrationMediator;

    @PostMapping("/students/{studentId}/course-classes/{courseClassId}")
    public ResponseEntity<ScoreResponse> registerStudentToCourseClass(
            @PathVariable Long studentId,
            @PathVariable Long courseClassId) {
        return ResponseEntity.ok(registrationMediator.registerStudentToCourseClass(studentId, courseClassId));
    }
}
