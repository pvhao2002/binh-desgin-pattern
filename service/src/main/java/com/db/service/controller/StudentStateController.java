package com.db.service.controller;

import com.db.service.service.StudentStateService;
import com.db.service.state.StudentState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentStateController {

    private final StudentStateService stateService;

    @GetMapping("/{id}/state")
    public ResponseEntity<Map<String, Object>> getState(@PathVariable Long id) {
        StudentState state = stateService.getStateForStudent(id);
        return ResponseEntity.ok(Map.of(
                "studentId", id,
                "status", state.getStatusName(),
                "canRegisterCourse", state.canRegisterCourse()
        ));
    }
}
