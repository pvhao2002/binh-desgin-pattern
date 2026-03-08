package com.db.service.service.impl;

import com.db.service.command.CreateScoreCommand;
import com.db.service.command.DeleteScoreCommand;
import com.db.service.command.UpdateScoreCommand;
import com.db.service.dto.response.ScoreResponse;
import com.db.service.entity.CourseClass;
import com.db.service.entity.Score;
import com.db.service.entity.Student;
import com.db.service.repository.CourseClassRepository;
import com.db.service.repository.ScoreRepository;
import com.db.service.repository.StudentRepository;
import com.db.service.service.ScoreService;
import com.db.service.strategy.ScoreCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository repository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final ScoreCalculationStrategy scoreCalculationStrategy;

    @Override
    @Transactional
    public ScoreResponse execute(CreateScoreCommand command) {
        var req = command.getRequest();
        Student student = studentRepository.findById(req.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        CourseClass courseClass = courseClassRepository.findById(req.getCourseClassId()).orElseThrow(() -> new RuntimeException("CourseClass not found"));
        var entity = Score.builder()
                .student(student)
                .courseClass(courseClass)
                .continuousAssessmentScore(req.getContinuousAssessmentScore())
                .finalExamScore(req.getFinalExamScore())
                .finalScore(scoreCalculationStrategy.calculate(req.getContinuousAssessmentScore(), req.getFinalExamScore()))
                .build();
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public ScoreResponse execute(UpdateScoreCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Score not found: " + command.getId()));
        var req = command.getRequest();
        Student student = studentRepository.findById(req.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        CourseClass courseClass = courseClassRepository.findById(req.getCourseClassId()).orElseThrow(() -> new RuntimeException("CourseClass not found"));
        entity.setStudent(student);
        entity.setCourseClass(courseClass);
        entity.setContinuousAssessmentScore(req.getContinuousAssessmentScore());
        entity.setFinalExamScore(req.getFinalExamScore());
        entity.setFinalScore(scoreCalculationStrategy.calculate(req.getContinuousAssessmentScore(), req.getFinalExamScore()));
        entity = repository.save(entity);
        return toResponse(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteScoreCommand command) {
        repository.deleteById(command.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Score not found: " + id));
        return toResponse(entity);
    }

    private ScoreResponse toResponse(Score e) {
        String courseClassLabel = e.getCourseClass().getSubject().getSubjectName() + " - " + e.getCourseClass().getSemester().getSemesterName();
        return ScoreResponse.builder()
                .scoreId(e.getScoreId())
                .studentId(e.getStudent().getStudentId())
                .studentName(e.getStudent().getFullName())
                .courseClassId(e.getCourseClass().getCourseClassId())
                .courseClassName(courseClassLabel)
                .continuousAssessmentScore(e.getContinuousAssessmentScore())
                .finalExamScore(e.getFinalExamScore())
                .finalScore(e.getFinalScore())
                .build();
    }
}
