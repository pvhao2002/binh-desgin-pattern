import { Injectable } from '@angular/core';
import { StudentState } from '../student-state';

@Injectable({ providedIn: 'root' })
export class ActiveStudentState implements StudentState {
  getStatusName(): string { return 'ACTIVE'; }
  canEdit(): boolean { return true; }
  canRegisterCourse(): boolean { return true; }
  canRestore(): boolean { return false; }
}
