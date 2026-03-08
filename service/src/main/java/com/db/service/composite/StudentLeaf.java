package com.db.service.composite;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class StudentLeaf implements AcademicComponent {

    private Long studentId;
    private String fullName;

    @Override
    public List<AcademicComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void add(AcademicComponent child) {
        throw new UnsupportedOperationException("Leaf cannot have children");
    }

    @Override
    public int count() {
        return 1;
    }
}
