export interface Semester {
  semesterId: number;
  semesterName: string;
  academicYear: string;
}

export interface SemesterRequest {
  semesterName: string;
  academicYear: string;
}
