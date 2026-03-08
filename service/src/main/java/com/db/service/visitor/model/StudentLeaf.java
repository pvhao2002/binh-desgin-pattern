package com.db.service.visitor.model;

import com.db.service.visitor.EntityVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLeaf implements AcademicComponent {
    private Long studentId;
    private String fullName;
    private String email;

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
