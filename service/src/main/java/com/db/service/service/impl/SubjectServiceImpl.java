package com.db.service.service.impl;

import com.db.service.command.CreateSubjectCommand;
import com.db.service.command.DeleteSubjectCommand;
import com.db.service.command.UpdateSubjectCommand;
import com.db.service.dto.response.SubjectResponse;
import com.db.service.entity.Department;
import com.db.service.entity.Subject;
import com.db.service.repository.DepartmentRepository;
import com.db.service.repository.SubjectRepository;
import com.db.service.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository repository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public SubjectResponse execute(CreateSubjectCommand command) {
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        var entity = Subject.builder()
                .subjectName(req.getSubjectName())
                .credits(req.getCredits())
                .department(dept)
                .description(req.getDescription())
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public SubjectResponse execute(UpdateSubjectCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Subject not found: " + command.getId()));
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        entity.setSubjectName(req.getSubjectName());
        entity.setCredits(req.getCredits());
        entity.setDepartment(dept);
        entity.setDescription(req.getDescription());
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteSubjectCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found: " + id));
        return toResponse(entity);
    }

    private SubjectResponse toResponse(Subject e) {
        return SubjectResponse.builder()
                .subjectId(e.getSubjectId())
                .subjectName(e.getSubjectName())
                .credits(e.getCredits())
                .departmentId(e.getDepartment().getDepartmentId())
                .departmentName(e.getDepartment().getDepartmentName())
                .description(e.getDescription())
                .build();
    }
}
