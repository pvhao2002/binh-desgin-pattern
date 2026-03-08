package com.db.service.controller;

import com.db.service.command.CreateSemesterCommand;
import com.db.service.command.DeleteSemesterCommand;
import com.db.service.command.UpdateSemesterCommand;
import com.db.service.dto.request.SemesterRequest;
import com.db.service.dto.response.SemesterResponse;
import com.db.service.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SemesterController {

    private final SemesterService service;

    @GetMapping
    public List<SemesterResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SemesterResponse> create(@RequestBody SemesterRequest request) {
        SemesterResponse created = service.execute(new CreateSemesterCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponse> update(@PathVariable Long id, @RequestBody SemesterRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateSemesterCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteSemesterCommand(id));
        return ResponseEntity.noContent().build();
    }
}
