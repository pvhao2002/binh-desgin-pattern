package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponse {
    private Long classId;
    private String className;
    private String courseYear;
    private Long departmentId;
    private String departmentName;
}
