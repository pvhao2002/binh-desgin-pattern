package com.db.service.service;

import com.db.service.command.CreateSubjectCommand;
import com.db.service.command.DeleteSubjectCommand;
import com.db.service.command.UpdateSubjectCommand;
import com.db.service.dto.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectResponse execute(CreateSubjectCommand command);
    SubjectResponse execute(UpdateSubjectCommand command);
    void execute(DeleteSubjectCommand command);
    List<SubjectResponse> findAll();
    SubjectResponse findById(Long id);
}
