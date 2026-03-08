package com.db.service.service.impl;

import com.db.service.command.CreateDepartmentCommand;
import com.db.service.command.DeleteDepartmentCommand;
import com.db.service.command.UpdateDepartmentCommand;
import com.db.service.dto.response.DepartmentResponse;
import com.db.service.entity.Department;
import com.db.service.repository.DepartmentRepository;
import com.db.service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    @Override
    @Transactional
    public DepartmentResponse execute(CreateDepartmentCommand command) {
        var req = command.getRequest();
        var entity = Department.builder()
                .departmentName(req.getDepartmentName())
                .establishedDate(req.getEstablishedDate())
                .description(req.getDescription())
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public DepartmentResponse execute(UpdateDepartmentCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Department not found: " + command.getId()));
        var req = command.getRequest();
        entity.setDepartmentName(req.getDepartmentName());
        entity.setEstablishedDate(req.getEstablishedDate());
        entity.setDescription(req.getDescription());
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteDepartmentCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Department not found: " + id));
        return toResponse(entity);
    }

    private DepartmentResponse toResponse(Department e) {
        return DepartmentResponse.builder()
                .departmentId(e.getDepartmentId())
                .departmentName(e.getDepartmentName())
                .establishedDate(e.getEstablishedDate())
                .description(e.getDescription())
                .build();
    }
}
