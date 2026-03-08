package com.db.service.service.impl;

import com.db.service.entity.Student;
import com.db.service.repository.StudentRepository;
import com.db.service.service.StudentStateService;
import com.db.service.state.StudentContext;
import com.db.service.state.StudentState;
import com.db.service.state.StudentStateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentStateServiceImpl implements StudentStateService {

    private final StudentRepository studentRepository;
    private final StudentStateFactory stateFactory;

    @Override
    @Transactional(readOnly = true)
    public StudentState getStateForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        return stateFactory.getState(student.getStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canStudentRegisterCourse(Long studentId) {
        StudentState state = getStateForStudent(studentId);
        StudentContext context = new StudentContext();
        context.setCurrentState(state);
        return state.canRegisterCourse();
    }
}
