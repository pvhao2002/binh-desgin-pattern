package com.db.service.interpreter.context;

import com.db.service.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterContext {
    private Student student;

    public FilterContext(Student student) {
        this.student = student;
    }
}
