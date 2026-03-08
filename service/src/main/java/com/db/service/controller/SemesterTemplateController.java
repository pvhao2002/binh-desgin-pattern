package com.db.service.controller;

import com.db.service.dto.response.SemesterResponse;
import com.db.service.prototype.SemesterTemplate;
import com.db.service.service.SemesterTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/semester-templates")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SemesterTemplateController {

    private final SemesterTemplateService templateService;

    @PostMapping("/create-from-template")
    public ResponseEntity<SemesterResponse> createFromTemplate(@RequestBody SemesterTemplate template) {
        SemesterResponse created = templateService.createFromTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
