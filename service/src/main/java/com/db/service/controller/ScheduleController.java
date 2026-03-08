package com.db.service.controller;

import com.db.service.command.CreateScheduleCommand;
import com.db.service.command.DeleteScheduleCommand;
import com.db.service.command.UpdateScheduleCommand;
import com.db.service.dto.request.ScheduleRequest;
import com.db.service.dto.response.ScheduleResponse;
import com.db.service.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping
    public List<ScheduleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody ScheduleRequest request) {
        ScheduleResponse created = service.execute(new CreateScheduleCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id, @RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateScheduleCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteScheduleCommand(id));
        return ResponseEntity.noContent().build();
    }
}
