import { Injectable } from '@angular/core';
import { StudentState } from '../student-state';

@Injectable({ providedIn: 'root' })
export class SuspendedStudentState implements StudentState {
  getStatusName(): string { return 'SUSPENDED'; }
  canEdit(): boolean { return true; }
  canRegisterCourse(): boolean { return false; }
  canRestore(): boolean { return false; }
}
