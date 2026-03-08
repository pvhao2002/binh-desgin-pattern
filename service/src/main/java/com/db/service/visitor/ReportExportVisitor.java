package com.db.service.visitor;

import com.db.service.visitor.model.ClassNode;
import com.db.service.visitor.model.DepartmentNode;
import com.db.service.visitor.model.StudentLeaf;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReportExportVisitor implements EntityVisitor {

    private final List<String> reportLines = new ArrayList<>();

    @Override
    public void visit(DepartmentNode node) {
        reportLines.add("DEPARTMENT: " + node.getDepartmentId() + " - " + node.getDepartmentName());
    }

    @Override
    public void visit(ClassNode node) {
        reportLines.add("  CLASS: " + node.getClassName() + " (" + node.getCourseYear() + ")");
    }

    @Override
    public void visit(StudentLeaf node) {
        reportLines.add("    STUDENT: " + node.getStudentId() + " - " + node.getFullName());
    }
}
