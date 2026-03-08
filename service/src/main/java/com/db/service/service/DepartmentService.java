package com.db.service.service;

import com.db.service.command.CreateDepartmentCommand;
import com.db.service.command.DeleteDepartmentCommand;
import com.db.service.command.UpdateDepartmentCommand;
import com.db.service.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse execute(CreateDepartmentCommand command);
    DepartmentResponse execute(UpdateDepartmentCommand command);
    void execute(DeleteDepartmentCommand command);
    List<DepartmentResponse> findAll();
    DepartmentResponse findById(Long id);
}
