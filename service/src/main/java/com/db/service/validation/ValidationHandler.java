package com.db.service.validation;

import com.db.service.dto.request.StudentRequest;

public interface ValidationHandler {
    ValidationResult validate(StudentRequest request);
    void setNext(ValidationHandler next);
}
