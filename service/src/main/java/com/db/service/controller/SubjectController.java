package com.db.service.controller;

import com.db.service.command.CreateSubjectCommand;
import com.db.service.command.DeleteSubjectCommand;
import com.db.service.command.UpdateSubjectCommand;
import com.db.service.dto.request.SubjectRequest;
import com.db.service.dto.response.SubjectResponse;
import com.db.service.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectService service;

    @GetMapping
    public List<SubjectResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> create(@RequestBody SubjectRequest request) {
        SubjectResponse created = service.execute(new CreateSubjectCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> update(@PathVariable Long id, @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateSubjectCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteSubjectCommand(id));
        return ResponseEntity.noContent().build();
    }
}
