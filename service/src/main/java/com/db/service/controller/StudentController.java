package com.db.service.controller;

import com.db.service.command.CreateStudentCommand;
import com.db.service.command.DeleteStudentCommand;
import com.db.service.command.UpdateStudentCommand;
import com.db.service.dto.request.StudentRequest;
import com.db.service.dto.response.StudentResponse;
import com.db.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService service;

    @GetMapping
    public List<StudentResponse> findAll(@RequestParam(required = false) String filter) {
        if (filter != null && !filter.isBlank()) {
            return service.findAllWithFilter(filter);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody StudentRequest request) {
        StudentResponse created = service.execute(new CreateStudentCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody StudentRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateStudentCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteStudentCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<StudentResponse> restore(@PathVariable Long id) {
        return ResponseEntity.ok(service.restoreFromMemento(id));
    }
}
