package com.db.service.validation;

import com.db.service.dto.request.StudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class StudentValidationPipeline {

    private final InputValidationHandler inputValidationHandler;
    private final BusinessValidationHandler businessValidationHandler;

    @PostConstruct
    public void init() {
        inputValidationHandler.setNext(businessValidationHandler);
    }

    public ValidationResult validate(StudentRequest request) {
        return inputValidationHandler.validate(request);
    }
}
