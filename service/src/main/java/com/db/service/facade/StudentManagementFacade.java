package com.db.service.facade;

import com.db.service.dto.response.ClassResponse;
import com.db.service.dto.response.ScoreResponse;
import com.db.service.dto.response.StudentOverviewResponse;
import com.db.service.dto.response.StudentResponse;
import com.db.service.service.ClassService;
import com.db.service.service.ScoreService;
import com.db.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentManagementFacade {

    private final StudentService studentService;
    private final ClassService classService;
    private final ScoreService scoreService;

    public StudentOverviewResponse getStudentOverview(Long studentId) {
        StudentResponse student = studentService.findById(studentId);
        ClassResponse classInfo = classService.findById(student.getClassId());
        List<ScoreResponse> scores = scoreService.findAll().stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .collect(Collectors.toList());
        return StudentOverviewResponse.builder()
                .student(student)
                .classInfo(classInfo)
                .scores(scores)
                .build();
    }
}
