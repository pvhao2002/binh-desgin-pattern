package com.db.service.service;

import com.db.service.command.CreateClassCommand;
import com.db.service.command.DeleteClassCommand;
import com.db.service.command.UpdateClassCommand;
import com.db.service.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    ClassResponse execute(CreateClassCommand command);
    ClassResponse execute(UpdateClassCommand command);
    void execute(DeleteClassCommand command);
    List<ClassResponse> findAll();
    ClassResponse findById(Long id);
}
