package com.db.service.decorator;

import com.db.service.command.CreateStudentCommand;
import com.db.service.command.DeleteStudentCommand;
import com.db.service.command.UpdateStudentCommand;
import com.db.service.dto.response.StudentResponse;
import com.db.service.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoggingStudentServiceDecorator implements StudentService {

    private final StudentService delegate;

    public LoggingStudentServiceDecorator(@Qualifier("studentServiceImpl") StudentService delegate) {
        this.delegate = delegate;
    }

    @Override
    public StudentResponse execute(CreateStudentCommand command) {
        log.debug("StudentService: create student");
        StudentResponse response = delegate.execute(command);
        log.debug("StudentService: created student id={}", response.getStudentId());
        return response;
    }

    @Override
    public StudentResponse execute(UpdateStudentCommand command) {
        log.debug("StudentService: update student id={}", command.getId());
        StudentResponse response = delegate.execute(command);
        log.debug("StudentService: updated student id={}", command.getId());
        return response;
    }

    @Override
    public void execute(DeleteStudentCommand command) {
        log.debug("StudentService: delete student id={}", command.getId());
        delegate.execute(command);
        log.debug("StudentService: deleted student id={}", command.getId());
    }

    @Override
    public List<StudentResponse> findAll() {
        log.debug("StudentService: findAll");
        return delegate.findAll();
    }

    @Override
    public List<StudentResponse> findAllWithFilter(String filter) {
        log.debug("StudentService: findAllWithFilter filter={}", filter);
        return delegate.findAllWithFilter(filter);
    }

    @Override
    public StudentResponse findById(Long id) {
        log.debug("StudentService: findById id={}", id);
        return delegate.findById(id);
    }

    @Override
    public StudentResponse restoreFromMemento(Long studentId) {
        log.debug("StudentService: restoreFromMemento studentId={}", studentId);
        return delegate.restoreFromMemento(studentId);
    }
}
