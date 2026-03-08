package com.db.service.service;

import com.db.service.state.StudentContext;
import com.db.service.state.StudentState;

public interface StudentStateService {
    StudentState getStateForStudent(Long studentId);
    boolean canStudentRegisterCourse(Long studentId);
}
