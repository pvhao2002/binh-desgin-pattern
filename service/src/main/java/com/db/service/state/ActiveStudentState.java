package com.db.service.state;

import org.springframework.stereotype.Component;

@Component
public class ActiveStudentState implements StudentState {
    @Override
    public boolean canRegisterCourse() {
        return true;
    }

    @Override
    public String getStatusName() {
        return "ACTIVE";
    }
}
