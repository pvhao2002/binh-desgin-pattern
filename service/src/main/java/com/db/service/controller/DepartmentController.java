package com.db.service.controller;

import com.db.service.command.CreateDepartmentCommand;
import com.db.service.command.DeleteDepartmentCommand;
import com.db.service.command.UpdateDepartmentCommand;
import com.db.service.dto.request.DepartmentRequest;
import com.db.service.dto.response.DepartmentResponse;
import com.db.service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    public List<DepartmentResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest request) {
        DepartmentResponse created = service.execute(new CreateDepartmentCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id, @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateDepartmentCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteDepartmentCommand(id));
        return ResponseEntity.noContent().build();
    }
}
