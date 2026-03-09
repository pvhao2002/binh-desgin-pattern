export interface StudentStateContext {
  status: string;
  canEdit(): boolean;
  canRegisterCourse(): boolean;
  canRestore(): boolean;
}

export interface StudentState {
  getStatusName(): string;
  canEdit(): boolean;
  canRegisterCourse(): boolean;
  canRestore(): boolean;
}
