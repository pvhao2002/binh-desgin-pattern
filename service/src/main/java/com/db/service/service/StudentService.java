package com.db.service.service;

import com.db.service.command.CreateStudentCommand;
import com.db.service.command.DeleteStudentCommand;
import com.db.service.command.UpdateStudentCommand;
import com.db.service.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse execute(CreateStudentCommand command);
    StudentResponse execute(UpdateStudentCommand command);
    void execute(DeleteStudentCommand command);
    List<StudentResponse> findAll();
    List<StudentResponse> findAllWithFilter(String filter);
    StudentResponse findById(Long id);
    StudentResponse restoreFromMemento(Long studentId);
}
