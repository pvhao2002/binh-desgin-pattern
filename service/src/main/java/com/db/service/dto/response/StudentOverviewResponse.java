package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentOverviewResponse {
    private StudentResponse student;
    private ClassResponse classInfo;
    private List<ScoreResponse> scores;
}
