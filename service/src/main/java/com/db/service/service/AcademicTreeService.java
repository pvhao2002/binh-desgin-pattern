package com.db.service.service;

import com.db.service.composite.AcademicComponent;

import java.util.List;

public interface AcademicTreeService {
    List<AcademicComponent> buildDepartmentTree();
}
