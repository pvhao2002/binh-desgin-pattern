import { Injectable } from '@angular/core';
import { StudentState } from '../student-state';

@Injectable({ providedIn: 'root' })
export class GraduatedStudentState implements StudentState {
  getStatusName(): string { return 'GRADUATED'; }
  canEdit(): boolean { return false; }
  canRegisterCourse(): boolean { return false; }
  canRestore(): boolean { return false; }
}
