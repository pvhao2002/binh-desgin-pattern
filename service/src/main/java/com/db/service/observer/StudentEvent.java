package com.db.service.observer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentEvent {
    public enum Type { CREATED, UPDATED, DELETED }
    private final Type type;
    private final Long studentId;
}
