package com.db.service.controller;

import com.db.service.command.CreateRoomCommand;
import com.db.service.command.DeleteRoomCommand;
import com.db.service.command.UpdateRoomCommand;
import com.db.service.dto.request.RoomRequest;
import com.db.service.dto.response.RoomResponse;
import com.db.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService service;

    @GetMapping
    public List<RoomResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        RoomResponse created = service.execute(new CreateRoomCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(service.execute(new UpdateRoomCommand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.execute(new DeleteRoomCommand(id));
        return ResponseEntity.noContent().build();
    }
}
