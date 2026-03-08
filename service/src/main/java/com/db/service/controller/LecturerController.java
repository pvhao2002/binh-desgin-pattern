package com.db.service.controller;

import com.db.service.command.CreateLecturerCommand;
import com.db.service.command.DeleteLecturerCommand;
import com.db.service.command.UpdateLecturerCommand;
import com.db.service.dto.request.LecturerRequest;
import com.db.service.dto.response.LecturerResponse;
import com.db.service.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LecturerController {

    private final LecturerService service;

    @GetMapping
    public List<LecturerResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LecturerResponse> create(@RequestBody LecturerRequest request) {
        LecturerResponse created = service.execute(new CreateLecturerCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerResponse> update(@PathVariable Long id, @RequestBody LecturerRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateLecturerCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteLecturerCommand(id));
        return ResponseEntity.noContent().build();
    }
}
