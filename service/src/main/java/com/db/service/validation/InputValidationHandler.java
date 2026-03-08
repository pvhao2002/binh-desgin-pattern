package com.db.service.validation;

import com.db.service.dto.request.StudentRequest;
import org.springframework.stereotype.Component;

@Component
public class InputValidationHandler implements ValidationHandler {

    private ValidationHandler next;

    @Override
    public ValidationResult validate(StudentRequest request) {
        ValidationResult result = ValidationResult.success();
        if (request == null) {
            result.addError("Request cannot be null");
            return result;
        }
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            result.addError("Full name is required");
        }
        if (request.getClassId() == null) {
            result.addError("Class is required");
        }
        if (request.getEmail() != null && !request.getEmail().isBlank() && !request.getEmail().contains("@")) {
            result.addError("Invalid email format");
        }
        if (!result.isValid()) {
            return result;
        }
        return next != null ? next.validate(request) : result;
    }

    @Override
    public void setNext(ValidationHandler next) {
        this.next = next;
    }
}
