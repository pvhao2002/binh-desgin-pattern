package com.db.service.strategy;

public interface ScoreCalculationStrategy {
    Double calculate(Double continuousAssessmentScore, Double finalExamScore);
}
