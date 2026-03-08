package com.db.service.controller;

import com.db.service.adapter.ExternalStudentSource;
import com.db.service.adapter.ExternalStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExternalSyncController {

    private final ExternalStudentSource externalStudentSource;

    @GetMapping("/external-students")
    public ResponseEntity<List<ExternalStudentDto>> fetchExternalStudents() {
        return ResponseEntity.ok(externalStudentSource.fetchStudents());
    }
}
