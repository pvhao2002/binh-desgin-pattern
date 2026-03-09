export interface Department {
  departmentId: number;
  departmentName: string;
  establishedDate: string | null;
  description: string | null;
}

export interface DepartmentRequest {
  departmentName: string;
  establishedDate: string | null;
  description: string | null;
}
