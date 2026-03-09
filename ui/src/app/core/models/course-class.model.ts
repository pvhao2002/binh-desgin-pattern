export interface CourseClass {
  courseClassId: number;
  subjectId: number;
  subjectName: string;
  semesterId: number;
  semesterName: string;
  classId: number;
  className: string;
  lecturerId: number;
  lecturerName: string;
}

export interface CourseClassRequest {
  subjectId: number;
  semesterId: number;
  classId: number;
  lecturerId: number;
}
