export interface Score {
  scoreId: number;
  studentId: number;
  studentName: string;
  courseClassId: number;
  courseClassName: string;
  continuousAssessmentScore: number | null;
  finalExamScore: number | null;
  finalScore: number | null;
}

export interface ScoreRequest {
  studentId: number;
  courseClassId: number;
  continuousAssessmentScore: number | null;
  finalExamScore: number | null;
}
