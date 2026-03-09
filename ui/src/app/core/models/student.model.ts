export interface Student {
  studentId: number;
  fullName: string;
  dateOfBirth: string | null;
  gender: string | null;
  address: string | null;
  phoneNumber: string | null;
  email: string | null;
  classId: number;
  className: string;
}

export interface StudentRequest {
  fullName: string;
  dateOfBirth: string | null;
  gender: string | null;
  address: string | null;
  phoneNumber: string | null;
  email: string | null;
  classId: number;
}
