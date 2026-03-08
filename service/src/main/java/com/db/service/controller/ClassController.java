package com.db.service.controller;

import com.db.service.command.CreateClassCommand;
import com.db.service.command.DeleteClassCommand;
import com.db.service.command.UpdateClassCommand;
import com.db.service.dto.request.ClassRequest;
import com.db.service.dto.response.ClassResponse;
import com.db.service.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClassController {

    private final ClassService service;

    @GetMapping
    public List<ClassResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClassResponse> create(@RequestBody ClassRequest request) {
        ClassResponse created = service.execute(new CreateClassCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponse> update(@PathVariable Long id, @RequestBody ClassRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateClassCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteClassCommand(id));
        return ResponseEntity.noContent().build();
    }
}
