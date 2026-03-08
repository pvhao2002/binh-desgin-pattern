package com.db.service.state;

import org.springframework.stereotype.Component;

@Component
public class StudentStateFactory {

    private final ActiveStudentState activeState;
    private final SuspendedStudentState suspendedState;
    private final GraduatedStudentState graduatedState;

    public StudentStateFactory(ActiveStudentState activeState,
                               SuspendedStudentState suspendedState,
                               GraduatedStudentState graduatedState) {
        this.activeState = activeState;
        this.suspendedState = suspendedState;
        this.graduatedState = graduatedState;
    }

    public StudentState getState(String status) {
        if (status == null) return activeState;
        return switch (status.toUpperCase()) {
            case "SUSPENDED" -> suspendedState;
            case "GRADUATED" -> graduatedState;
            default -> activeState;
        };
    }
}
