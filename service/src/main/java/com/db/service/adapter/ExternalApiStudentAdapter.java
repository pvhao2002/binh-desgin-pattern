package com.db.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExternalApiStudentAdapter implements ExternalStudentSource {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiStudentAdapter.class);

    @Value("${external.student-api.url:}")
    private String externalApiUrl;

    @Override
    public List<ExternalStudentDto> fetchStudents() {
        if (externalApiUrl == null || externalApiUrl.isBlank()) {
            return getMockStudents();
        }
        try {
            return fetchFromApi();
        } catch (Exception e) {
            log.warn("External API unavailable, using mock data: {}", e.getMessage());
            return getMockStudents();
        }
    }

    private List<ExternalStudentDto> fetchFromApi() {
        return getMockStudents();
    }

    private List<ExternalStudentDto> getMockStudents() {
        List<ExternalStudentDto> list = new ArrayList<>();
        list.add(new ExternalStudentDto(0L, "External Student 1", "ext1@example.com"));
        list.add(new ExternalStudentDto(0L, "External Student 2", "ext2@example.com"));
        return list;
    }
}
