package com.db.service.service.impl;

import com.db.service.command.CreateCourseClassCommand;
import com.db.service.command.DeleteCourseClassCommand;
import com.db.service.command.UpdateCourseClassCommand;
import com.db.service.dto.response.CourseClassResponse;
import com.db.service.entity.*;
import com.db.service.repository.*;
import com.db.service.service.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseClassServiceImpl implements CourseClassService {

    private final CourseClassRepository repository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final ClassRepository classRepository;
    private final LecturerRepository lecturerRepository;

    @Override
    @Transactional
    public CourseClassResponse execute(CreateCourseClassCommand command) {
        var req = command.getRequest();
        Subject subject = subjectRepository.findById(req.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));
        Semester semester = semesterRepository.findById(req.getSemesterId()).orElseThrow(() -> new RuntimeException("Semester not found"));
        ClassEntity clazz = classRepository.findById(req.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        Lecturer lecturer = lecturerRepository.findById(req.getLecturerId()).orElseThrow(() -> new RuntimeException("Lecturer not found"));
        var entity = CourseClass.builder()
                .subject(subject)
                .semester(semester)
                .classEntity(clazz)
                .lecturer(lecturer)
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public CourseClassResponse execute(UpdateCourseClassCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("CourseClass not found: " + command.getId()));
        var req = command.getRequest();
        Subject subject = subjectRepository.findById(req.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));
        Semester semester = semesterRepository.findById(req.getSemesterId()).orElseThrow(() -> new RuntimeException("Semester not found"));
        ClassEntity clazz = classRepository.findById(req.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        Lecturer lecturer = lecturerRepository.findById(req.getLecturerId()).orElseThrow(() -> new RuntimeException("Lecturer not found"));
        entity.setSubject(subject);
        entity.setSemester(semester);
        entity.setClassEntity(clazz);
        entity.setLecturer(lecturer);
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteCourseClassCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseClassResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseClassResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("CourseClass not found: " + id));
        return toResponse(entity);
    }

    private CourseClassResponse toResponse(CourseClass e) {
        return CourseClassResponse.builder()
                .courseClassId(e.getCourseClassId())
                .subjectId(e.getSubject().getSubjectId())
                .subjectName(e.getSubject().getSubjectName())
                .semesterId(e.getSemester().getSemesterId())
                .semesterName(e.getSemester().getSemesterName())
                .classId(e.getClassEntity().getClassId())
                .className(e.getClassEntity().getClassName())
                .lecturerId(e.getLecturer().getLecturerId())
                .lecturerName(e.getLecturer().getLecturerName())
                .build();
    }
}
