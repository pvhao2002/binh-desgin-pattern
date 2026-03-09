export interface Lecturer {
  lecturerId: number;
  lecturerName: string;
  academicDegree: string | null;
  email: string | null;
  phoneNumber: string | null;
  departmentId: number;
  departmentName: string;
}

export interface LecturerRequest {
  lecturerName: string;
  academicDegree: string | null;
  email: string | null;
  phoneNumber: string | null;
  departmentId: number;
}
