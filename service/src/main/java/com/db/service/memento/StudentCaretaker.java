package com.db.service.memento;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StudentCaretaker {

    private final Map<Long, StudentMemento> mementos = new ConcurrentHashMap<>();

    public void saveMemento(Long studentId, StudentMemento memento) {
        mementos.put(studentId, memento);
    }

    public StudentMemento getMemento(Long studentId) {
        return mementos.get(studentId);
    }

    public void clearMemento(Long studentId) {
        mementos.remove(studentId);
    }
}
