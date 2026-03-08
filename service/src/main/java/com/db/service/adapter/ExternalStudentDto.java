package com.db.service.adapter;

public class ExternalStudentDto {
    private final Long studentId;
    private final String fullName;
    private final String email;

    public ExternalStudentDto(Long studentId, String fullName, String email) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
}
