package com.db.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefaultScoreCalculationStrategy implements ScoreCalculationStrategy {

    private static final double CONTINUOUS_WEIGHT = 0.3;
    private static final double FINAL_WEIGHT = 0.7;

    @Override
    public Double calculate(Double continuousAssessmentScore, Double finalExamScore) {
        if (continuousAssessmentScore == null && finalExamScore == null) return null;
        if (continuousAssessmentScore == null) return finalExamScore;
        if (finalExamScore == null) return continuousAssessmentScore;
        return CONTINUOUS_WEIGHT * continuousAssessmentScore + FINAL_WEIGHT * finalExamScore;
    }
}
