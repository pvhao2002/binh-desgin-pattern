package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreResponse {
    private Long scoreId;
    private Long studentId;
    private String studentName;
    private Long courseClassId;
    private String courseClassName;
    private Double continuousAssessmentScore;
    private Double finalExamScore;
    private Double finalScore;
}
