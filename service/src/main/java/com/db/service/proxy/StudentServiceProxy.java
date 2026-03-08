package com.db.service.proxy;

import com.db.service.command.CreateStudentCommand;
import com.db.service.command.DeleteStudentCommand;
import com.db.service.command.UpdateStudentCommand;
import com.db.service.dto.response.StudentResponse;
import com.db.service.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Primary
public class StudentServiceProxy implements StudentService {

    private final StudentService realService;
    private final Map<Long, StudentResponse> getByIdCache = new ConcurrentHashMap<>();

    public StudentServiceProxy(@Qualifier("loggingStudentServiceDecorator") StudentService realService) {
        this.realService = realService;
    }

    @Override
    public StudentResponse execute(CreateStudentCommand command) {
        return realService.execute(command);
    }

    @Override
    public StudentResponse execute(UpdateStudentCommand command) {
        getByIdCache.remove(command.getId());
        return realService.execute(command);
    }

    @Override
    public void execute(DeleteStudentCommand command) {
        getByIdCache.remove(command.getId());
        realService.execute(command);
    }

    @Override
    public List<StudentResponse> findAll() {
        return realService.findAll();
    }

    @Override
    public List<StudentResponse> findAllWithFilter(String filter) {
        return realService.findAllWithFilter(filter);
    }

    @Override
    public StudentResponse findById(Long id) {
        return getByIdCache.computeIfAbsent(id, realService::findById);
    }

    @Override
    public StudentResponse restoreFromMemento(Long studentId) {
        getByIdCache.remove(studentId);
        return realService.restoreFromMemento(studentId);
    }
}
