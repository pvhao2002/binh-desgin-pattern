package com.db.service.composite;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentNode implements AcademicComponent {

    private Long departmentId;
    private String departmentName;
    private final List<AcademicComponent> children = new ArrayList<>();

    @Override
    public List<AcademicComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public void add(AcademicComponent child) {
        children.add(child);
    }

    @Override
    public int count() {
        return 1 + children.stream().mapToInt(AcademicComponent::count).sum();
    }
}
