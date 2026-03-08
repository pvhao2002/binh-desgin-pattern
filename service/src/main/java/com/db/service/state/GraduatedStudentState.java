package com.db.service.state;

import org.springframework.stereotype.Component;

@Component
public class GraduatedStudentState implements StudentState {
    @Override
    public boolean canRegisterCourse() {
        return false;
    }

    @Override
    public String getStatusName() {
        return "GRADUATED";
    }
}
