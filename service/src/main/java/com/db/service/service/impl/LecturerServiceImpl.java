package com.db.service.service.impl;

import com.db.service.command.CreateLecturerCommand;
import com.db.service.command.DeleteLecturerCommand;
import com.db.service.command.UpdateLecturerCommand;
import com.db.service.dto.response.LecturerResponse;
import com.db.service.entity.Department;
import com.db.service.entity.Lecturer;
import com.db.service.repository.DepartmentRepository;
import com.db.service.repository.LecturerRepository;
import com.db.service.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository repository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public LecturerResponse execute(CreateLecturerCommand command) {
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        var entity = Lecturer.builder()
                .lecturerName(req.getLecturerName())
                .academicDegree(req.getAcademicDegree())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .department(dept)
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public LecturerResponse execute(UpdateLecturerCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Lecturer not found: " + command.getId()));
        var req = command.getRequest();
        Department dept = departmentRepository.findById(req.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        entity.setLecturerName(req.getLecturerName());
        entity.setAcademicDegree(req.getAcademicDegree());
        entity.setEmail(req.getEmail());
        entity.setPhoneNumber(req.getPhoneNumber());
        entity.setDepartment(dept);
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteLecturerCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturerResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LecturerResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Lecturer not found: " + id));
        return toResponse(entity);
    }

    private LecturerResponse toResponse(Lecturer e) {
        return LecturerResponse.builder()
                .lecturerId(e.getLecturerId())
                .lecturerName(e.getLecturerName())
                .academicDegree(e.getAcademicDegree())
                .email(e.getEmail())
                .phoneNumber(e.getPhoneNumber())
                .departmentId(e.getDepartment().getDepartmentId())
                .departmentName(e.getDepartment().getDepartmentName())
                .build();
    }
}
