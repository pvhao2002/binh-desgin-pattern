package com.db.service.service;

import com.db.service.visitor.model.DepartmentNode;

import java.util.List;

public interface ReportService {
    List<String> exportDepartmentTreeReport();
}
