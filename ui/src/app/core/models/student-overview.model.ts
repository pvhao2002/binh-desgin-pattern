import { Student } from './student.model';
import { Score } from './score.model';

export interface ClassInfo {
  classId: number;
  className: string;
  courseYear: string;
  departmentId?: number;
  departmentName?: string;
}

export interface StudentOverview {
  student: Student;
  classInfo: ClassInfo;
  scores: Score[];
}
