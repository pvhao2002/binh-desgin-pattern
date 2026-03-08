package com.db.service.service;

import com.db.service.command.CreateSemesterCommand;
import com.db.service.command.DeleteSemesterCommand;
import com.db.service.command.UpdateSemesterCommand;
import com.db.service.dto.response.SemesterResponse;

import java.util.List;

public interface SemesterService {
    SemesterResponse execute(CreateSemesterCommand command);
    SemesterResponse execute(UpdateSemesterCommand command);
    void execute(DeleteSemesterCommand command);
    List<SemesterResponse> findAll();
    SemesterResponse findById(Long id);
}
