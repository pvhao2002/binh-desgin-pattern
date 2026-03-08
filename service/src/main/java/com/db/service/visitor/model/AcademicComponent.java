package com.db.service.visitor.model;

import com.db.service.visitor.EntityVisitor;

public interface AcademicComponent {
    void accept(EntityVisitor visitor);
}
