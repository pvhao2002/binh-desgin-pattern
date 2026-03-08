package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerResponse {
    private Long lecturerId;
    private String lecturerName;
    private String academicDegree;
    private String email;
    private String phoneNumber;
    private Long departmentId;
    private String departmentName;
}
