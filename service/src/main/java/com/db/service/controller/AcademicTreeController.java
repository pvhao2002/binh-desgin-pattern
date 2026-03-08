package com.db.service.controller;

import com.db.service.composite.AcademicComponent;
import com.db.service.service.AcademicTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/academic-tree")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AcademicTreeController {

    private final AcademicTreeService academicTreeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTree() {
        List<AcademicComponent> roots = academicTreeService.buildDepartmentTree();
        int totalCount = roots.stream().mapToInt(AcademicComponent::count).sum();
        return ResponseEntity.ok(Map.of("roots", roots.size(), "totalNodes", totalCount));
    }
}
