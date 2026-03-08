package com.db.service.controller;

import com.db.service.template.StudentImportTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImportController {

    private final StudentImportTemplate studentImportTemplate;

    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> importStudents(@RequestBody String csvBody) {
        var input = new ByteArrayInputStream(csvBody.getBytes(StandardCharsets.UTF_8));
        int count = studentImportTemplate.importData(input);
        return ResponseEntity.ok(Map.of("imported", count));
    }
}
