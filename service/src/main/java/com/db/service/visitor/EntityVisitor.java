package com.db.service.visitor;

import com.db.service.visitor.model.DepartmentNode;
import com.db.service.visitor.model.ClassNode;
import com.db.service.visitor.model.StudentLeaf;

public interface EntityVisitor {
    void visit(DepartmentNode node);
    void visit(ClassNode node);
    void visit(StudentLeaf node);
}
