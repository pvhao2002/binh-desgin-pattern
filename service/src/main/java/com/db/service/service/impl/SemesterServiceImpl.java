package com.db.service.service.impl;

import com.db.service.command.CreateSemesterCommand;
import com.db.service.command.DeleteSemesterCommand;
import com.db.service.command.UpdateSemesterCommand;
import com.db.service.dto.response.SemesterResponse;
import com.db.service.entity.Semester;
import com.db.service.repository.SemesterRepository;
import com.db.service.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository repository;

    @Override
    @Transactional
    public SemesterResponse execute(CreateSemesterCommand command) {
        var req = command.getRequest();
        var entity = Semester.builder()
                .semesterName(req.getSemesterName())
                .academicYear(req.getAcademicYear())
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public SemesterResponse execute(UpdateSemesterCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Semester not found: " + command.getId()));
        var req = command.getRequest();
        entity.setSemesterName(req.getSemesterName());
        entity.setAcademicYear(req.getAcademicYear());
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteSemesterCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Semester not found: " + id));
        return toResponse(entity);
    }

    private SemesterResponse toResponse(Semester e) {
        return SemesterResponse.builder()
                .semesterId(e.getSemesterId())
                .semesterName(e.getSemesterName())
                .academicYear(e.getAcademicYear())
                .build();
    }
}
