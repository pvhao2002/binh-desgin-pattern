package com.db.service.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentEventPublisher {

    private final List<StudentEventListener> listeners = new ArrayList<>();

    public void addListener(StudentEventListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(StudentEvent event) {
        for (StudentEventListener listener : listeners) {
            listener.onStudentEvent(event);
        }
    }
}
