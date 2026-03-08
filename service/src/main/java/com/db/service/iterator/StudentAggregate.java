package com.db.service.iterator;

import com.db.service.dto.response.StudentResponse;
import com.db.service.entity.Student;

import java.util.Iterator;

public interface StudentAggregate {
    Iterator<StudentResponse> iterator();
}
