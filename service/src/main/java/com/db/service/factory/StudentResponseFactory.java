package com.db.service.factory;

import com.db.service.dto.request.StudentRequest;
import com.db.service.dto.response.StudentResponse;
import com.db.service.entity.Student;

public interface StudentResponseFactory {
    StudentResponse createFromEntity(Student entity);
    StudentResponse createFromRequest(StudentRequest request, String className);
}
