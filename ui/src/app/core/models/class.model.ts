export interface ClassItem {
  classId: number;
  className: string;
  courseYear: string;
  departmentId: number;
  departmentName: string;
}

export interface ClassRequest {
  className: string;
  courseYear: string;
  departmentId: number;
}
