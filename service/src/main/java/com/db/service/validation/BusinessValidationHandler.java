package com.db.service.validation;

import com.db.service.dto.request.StudentRequest;
import com.db.service.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessValidationHandler implements ValidationHandler {

    private final ClassRepository classRepository;
    private ValidationHandler next;

    @Override
    public ValidationResult validate(StudentRequest request) {
        ValidationResult result = ValidationResult.success();
        if (request.getClassId() != null && !classRepository.existsById(request.getClassId())) {
            result.addError("Class not found: " + request.getClassId());
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
