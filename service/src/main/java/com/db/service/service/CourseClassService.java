package com.db.service.service;

import com.db.service.command.CreateCourseClassCommand;
import com.db.service.command.DeleteCourseClassCommand;
import com.db.service.command.UpdateCourseClassCommand;
import com.db.service.dto.response.CourseClassResponse;

import java.util.List;

public interface CourseClassService {
    CourseClassResponse execute(CreateCourseClassCommand command);
    CourseClassResponse execute(UpdateCourseClassCommand command);
    void execute(DeleteCourseClassCommand command);
    List<CourseClassResponse> findAll();
    CourseClassResponse findById(Long id);
}
