package com.db.service.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class LoggingStudentListener implements StudentEventListener {

    private static final Logger log = LoggerFactory.getLogger(LoggingStudentListener.class);

    private final StudentEventPublisher publisher;

    public LoggingStudentListener(StudentEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void register() {
        publisher.addListener(this);
    }

    @Override
    public void onStudentEvent(StudentEvent event) {
        log.info("Student event: {} for studentId={}", event.getType(), event.getStudentId());
    }
}
