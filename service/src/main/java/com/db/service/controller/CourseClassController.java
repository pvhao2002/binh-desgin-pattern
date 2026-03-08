package com.db.service.controller;

import com.db.service.command.CreateCourseClassCommand;
import com.db.service.command.DeleteCourseClassCommand;
import com.db.service.command.UpdateCourseClassCommand;
import com.db.service.dto.request.CourseClassRequest;
import com.db.service.dto.response.CourseClassResponse;
import com.db.service.service.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course-classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CourseClassController {

    private final CourseClassService service;

    @GetMapping
    public List<CourseClassResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseClassResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CourseClassResponse> create(@RequestBody CourseClassRequest request) {
        CourseClassResponse created = service.execute(new CreateCourseClassCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseClassResponse> update(@PathVariable Long id, @RequestBody CourseClassRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateCourseClassCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteCourseClassCommand(id));
        return ResponseEntity.noContent().build();
    }
}
