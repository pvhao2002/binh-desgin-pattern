package com.db.service.service.impl;

import com.db.service.command.CreateClassCommand;
import com.db.service.command.DeleteClassCommand;
import com.db.service.command.UpdateClassCommand;
import com.db.service.dto.response.ClassResponse;
import com.db.service.entity.ClassEntity;
import com.db.service.entity.Department;
import com.db.service.repository.ClassRepository;
import com.db.service.repository.DepartmentRepository;
import com.db.service.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository repository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public ClassResponse execute(CreateClassCommand command) {
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        var entity = ClassEntity.builder()
                .className(req.getClassName())
                .courseYear(req.getCourseYear())
                .department(dept)
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public ClassResponse execute(UpdateClassCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Class not found: " + command.getId()));
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        entity.setClassName(req.getClassName());
        entity.setCourseYear(req.getCourseYear());
        entity.setDepartment(dept);
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteClassCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClassResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Class not found: " + id));
        return toResponse(entity);
    }

    private ClassResponse toResponse(ClassEntity e) {
        return ClassResponse.builder()
                .classId(e.getClassId())
                .className(e.getClassName())
                .courseYear(e.getCourseYear())
                .departmentId(e.getDepartment().getDepartmentId())
                .departmentName(e.getDepartment().getDepartmentName())
                .build();
    }
}
