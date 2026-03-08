package com.db.service.visitor.model;

import com.db.service.visitor.EntityVisitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentNode implements AcademicComponent {
    private Long departmentId;
    private String departmentName;
    private List<ClassNode> classes = new ArrayList<>();

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
        for (ClassNode c : classes) {
            c.accept(visitor);
        }
    }
}
