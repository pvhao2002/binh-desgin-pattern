package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseClassResponse {
    private Long courseClassId;
    private Long subjectId;
    private String subjectName;
    private Long semesterId;
    private String semesterName;
    private Long classId;
    private String className;
    private Long lecturerId;
    private String lecturerName;
}
