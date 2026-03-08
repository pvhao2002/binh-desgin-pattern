package com.db.service.controller;

import com.db.service.command.CreateScoreCommand;
import com.db.service.command.DeleteScoreCommand;
import com.db.service.command.UpdateScoreCommand;
import com.db.service.dto.request.ScoreRequest;
import com.db.service.dto.response.ScoreResponse;
import com.db.service.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScoreController {

    private final ScoreService service;

    @GetMapping
    public List<ScoreResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScoreResponse> create(@RequestBody ScoreRequest request) {
        ScoreResponse created = service.execute(new CreateScoreCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreResponse> update(@PathVariable Long id, @RequestBody ScoreRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateScoreCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteScoreCommand(id));
        return ResponseEntity.noContent().build();
    }
}
