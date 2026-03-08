package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponse {
    private Long subjectId;
    private String subjectName;
    private Integer credits;
    private Long departmentId;
    private String departmentName;
    private String description;
}
