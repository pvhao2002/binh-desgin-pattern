package com.db.service.state;

import com.db.service.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentContext {
    private Student student;
    private StudentState currentState;
}
