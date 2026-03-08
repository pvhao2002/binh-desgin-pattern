package com.db.service.service;

import com.db.service.command.CreateLecturerCommand;
import com.db.service.command.DeleteLecturerCommand;
import com.db.service.command.UpdateLecturerCommand;
import com.db.service.dto.response.LecturerResponse;

import java.util.List;

public interface LecturerService {
    LecturerResponse execute(CreateLecturerCommand command);
    LecturerResponse execute(UpdateLecturerCommand command);
    void execute(DeleteLecturerCommand command);
    List<LecturerResponse> findAll();
    LecturerResponse findById(Long id);
}
