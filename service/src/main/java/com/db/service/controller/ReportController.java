package com.db.service.controller;

import com.db.service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/department-tree")
    public ResponseEntity<List<String>> departmentTreeReport() {
        return ResponseEntity.ok(reportService.exportDepartmentTreeReport());
    }
}
