package com.db.service.factory.impl;

import com.db.service.dto.request.StudentRequest;
import com.db.service.dto.response.StudentResponse;
import com.db.service.entity.Student;
import com.db.service.factory.StudentResponseFactory;
import org.springframework.stereotype.Component;

@Component
public class StudentResponseFactoryImpl implements StudentResponseFactory {

    @Override
    public StudentResponse createFromEntity(Student entity) {
        return StudentResponse.builder()
                .studentId(entity.getStudentId())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .classId(entity.getClassEntity().getClassId())
                .className(entity.getClassEntity().getClassName())
                .build();
    }

    @Override
    public StudentResponse createFromRequest(StudentRequest request, String className) {
        return StudentResponse.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .classId(request.getClassId())
                .className(className)
                .build();
    }
}
