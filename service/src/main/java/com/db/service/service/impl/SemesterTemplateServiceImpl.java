package com.db.service.service.impl;

import com.db.service.dto.response.SemesterResponse;
import com.db.service.entity.Semester;
import com.db.service.prototype.SemesterTemplate;
import com.db.service.repository.SemesterRepository;
import com.db.service.service.SemesterTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SemesterTemplateServiceImpl implements SemesterTemplateService {

    private final SemesterRepository repository;

    @Override
    @Transactional
    public SemesterResponse createFromTemplate(SemesterTemplate template) {
        SemesterTemplate cloned = template.copy();
        var entity = Semester.builder()
                .semesterName(cloned.getSemesterName())
                .academicYear(cloned.getAcademicYear())
                .build();
        entity = repository.save(entity);
        return SemesterResponse.builder()
                .semesterId(entity.getSemesterId())
                .semesterName(entity.getSemesterName())
                .academicYear(entity.getAcademicYear())
                .build();
    }
}
