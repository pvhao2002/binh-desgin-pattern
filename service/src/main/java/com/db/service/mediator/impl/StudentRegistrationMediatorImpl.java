package com.db.service.mediator.impl;

import com.db.service.command.CreateScoreCommand;
import com.db.service.dto.request.ScoreRequest;
import com.db.service.dto.response.CourseClassResponse;
import com.db.service.dto.response.ScoreResponse;
import com.db.service.dto.response.StudentResponse;
import com.db.service.mediator.StudentRegistrationMediator;
import com.db.service.service.CourseClassService;
import com.db.service.service.ScoreService;
import com.db.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentRegistrationMediatorImpl implements StudentRegistrationMediator {

    private final StudentService studentService;
    private final CourseClassService courseClassService;
    private final ScoreService scoreService;

    @Override
    public ScoreResponse registerStudentToCourseClass(Long studentId, Long courseClassId) {
        studentService.findById(studentId);
        courseClassService.findById(courseClassId);
        ScoreRequest request = ScoreRequest.builder()
                .studentId(studentId)
                .courseClassId(courseClassId)
                .build();
        return scoreService.execute(new CreateScoreCommand(request));
    }
}
