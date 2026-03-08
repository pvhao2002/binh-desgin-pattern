package com.db.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRequest {
    private Long studentId;
    private Long courseClassId;
    private Double continuousAssessmentScore;
    private Double finalExamScore;
}
