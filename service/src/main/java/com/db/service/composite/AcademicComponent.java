package com.db.service.composite;

import java.util.List;

public interface AcademicComponent {

    List<AcademicComponent> getChildren();

    void add(AcademicComponent child);

    int count();
}
