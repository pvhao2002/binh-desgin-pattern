package com.db.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseClassRequest {
    private Long subjectId;
    private Long semesterId;
    private Long classId;
    private Long lecturerId;
}
