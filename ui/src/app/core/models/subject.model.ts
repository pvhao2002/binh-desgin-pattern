export interface Subject {
  subjectId: number;
  subjectName: string;
  credits: number | null;
  departmentId: number;
  departmentName: string;
  description: string | null;
}

export interface SubjectRequest {
  subjectName: string;
  credits: number | null;
  departmentId: number;
  description: string | null;
}
