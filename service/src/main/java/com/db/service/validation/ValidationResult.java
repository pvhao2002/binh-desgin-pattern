package com.db.service.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private boolean valid;
    private List<String> errors = new ArrayList<>();

    public static ValidationResult success() {
        return new ValidationResult(true, new ArrayList<>());
    }

    public static ValidationResult failure(String... messages) {
        return new ValidationResult(false, new ArrayList<>(List.of(messages)));
    }

    public void addError(String message) {
        errors.add(message);
        valid = false;
    }
}
