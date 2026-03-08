package com.db.service.visitor.model;

import com.db.service.visitor.EntityVisitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClassNode implements AcademicComponent {
    private Long classId;
    private String className;
    private String courseYear;
    private List<StudentLeaf> students = new ArrayList<>();

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
        for (StudentLeaf s : students) {
            s.accept(visitor);
        }
    }
}
