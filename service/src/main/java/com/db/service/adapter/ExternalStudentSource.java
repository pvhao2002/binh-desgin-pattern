package com.db.service.adapter;

import java.util.List;

public interface ExternalStudentSource {
    List<ExternalStudentDto> fetchStudents();
}
