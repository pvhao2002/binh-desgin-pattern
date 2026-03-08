package com.db.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerRequest {
    private String lecturerName;
    private String academicDegree;
    private String email;
    private String phoneNumber;
    private Long departmentId;
}
