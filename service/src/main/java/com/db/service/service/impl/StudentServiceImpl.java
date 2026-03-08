package com.db.service.service.impl;

import com.db.service.command.CreateStudentCommand;
import com.db.service.command.DeleteStudentCommand;
import com.db.service.command.UpdateStudentCommand;
import com.db.service.dto.response.StudentResponse;
import com.db.service.entity.ClassEntity;
import com.db.service.entity.Student;
import com.db.service.factory.StudentResponseFactory;
import com.db.service.memento.StudentCaretaker;
import com.db.service.memento.StudentMemento;
import com.db.service.observer.StudentEvent;
import com.db.service.observer.StudentEventPublisher;
import com.db.service.bridge.StudentRepositoryBridge;
import com.db.service.repository.ClassRepository;
import com.db.service.interpreter.FilterParser;
import com.db.service.interpreter.Expression;
import com.db.service.interpreter.context.FilterContext;
import com.db.service.service.StudentService;
import com.db.service.validation.StudentValidationPipeline;
import com.db.service.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepositoryBridge repository;
    private final ClassRepository classRepository;
    private final StudentValidationPipeline validationPipeline;
    private final StudentResponseFactory responseFactory;
    private final StudentEventPublisher eventPublisher;
    private final StudentCaretaker mementoCaretaker;

    @Override
    @Transactional
    public StudentResponse execute(CreateStudentCommand command) {
        var req = command.getRequest();
        ValidationResult vr = validationPipeline.validate(req);
        if (!vr.isValid()) {
            throw new RuntimeException("Validation failed: " + String.join("; ", vr.getErrors()));
        }
        ClassEntity clazz = classRepository.findById(req.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        var entity = Student.builder()
                .fullName(req.getFullName())
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender())
                .address(req.getAddress())
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .classEntity(clazz)
                .build();
        entity = repository.save(entity);
        eventPublisher.notifyListeners(new StudentEvent(StudentEvent.Type.CREATED, entity.getStudentId()));
        return responseFactory.createFromEntity(entity);
    }

    @Override
    @Transactional
    public StudentResponse execute(UpdateStudentCommand command) {
        var entity = repository.findById(command.getId()).orElseThrow(() -> new RuntimeException("Student not found: " + command.getId()));
        StudentMemento memento = new StudentMemento(
                entity.getStudentId(), entity.getFullName(), entity.getDateOfBirth(), entity.getGender(),
                entity.getAddress(), entity.getPhoneNumber(), entity.getEmail(),
                entity.getClassEntity().getClassId()
        );
        mementoCaretaker.saveMemento(entity.getStudentId(), memento);
        var req = command.getRequest();
        ValidationResult vr = validationPipeline.validate(req);
        if (!vr.isValid()) {
            throw new RuntimeException("Validation failed: " + String.join("; ", vr.getErrors()));
        }
        ClassEntity clazz = classRepository.findById(req.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        entity.setFullName(req.getFullName());
        entity.setDateOfBirth(req.getDateOfBirth());
        entity.setGender(req.getGender());
        entity.setAddress(req.getAddress());
        entity.setPhoneNumber(req.getPhoneNumber());
        entity.setEmail(req.getEmail());
        entity.setClassEntity(clazz);
        entity = repository.save(entity);
        eventPublisher.notifyListeners(new StudentEvent(StudentEvent.Type.UPDATED, entity.getStudentId()));
        return responseFactory.createFromEntity(entity);
    }

    @Override
    @Transactional
    public void execute(DeleteStudentCommand command) {
        Long id = command.getId();
        repository.deleteById(id);
        eventPublisher.notifyListeners(new StudentEvent(StudentEvent.Type.DELETED, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findAll() {
        return repository.findAll().stream().map(responseFactory::createFromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findAllWithFilter(String filter) {
        Expression expression = FilterParser.parse(filter);
        FilterContext ctx = new FilterContext(null);
        return repository.findAll().stream()
                .filter(s -> {
                    ctx.setStudent(s);
                    return expression.interpret(ctx);
                })
                .map(responseFactory::createFromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found: " + id));
        return responseFactory.createFromEntity(entity);
    }

    @Override
    @Transactional
    public StudentResponse restoreFromMemento(Long studentId) {
        StudentMemento memento = mementoCaretaker.getMemento(studentId);
        if (memento == null) throw new RuntimeException("No memento found for student: " + studentId);
        var entity = repository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found: " + studentId));
        ClassEntity clazz = classRepository.findById(memento.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
        entity.setFullName(memento.getFullName());
        entity.setDateOfBirth(memento.getDateOfBirth());
        entity.setGender(memento.getGender());
        entity.setAddress(memento.getAddress());
        entity.setPhoneNumber(memento.getPhoneNumber());
        entity.setEmail(memento.getEmail());
        entity.setClassEntity(clazz);
        entity = repository.save(entity);
        mementoCaretaker.clearMemento(studentId);
        return responseFactory.createFromEntity(entity);
    }
}
