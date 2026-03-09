import { Injectable } from '@angular/core';
import { StudentState } from './student-state';
import { ActiveStudentState } from './impl/active-student.state';
import { SuspendedStudentState } from './impl/suspended-student.state';
import { GraduatedStudentState } from './impl/graduated-student.state';

@Injectable({ providedIn: 'root' })
export class StudentStateContextService {
  constructor(
    private activeState: ActiveStudentState,
    private suspendedState: SuspendedStudentState,
    private graduatedState: GraduatedStudentState
  ) {}

  getStateForStatus(status: string): StudentState {
    const s = (status ?? '').toUpperCase();
    if (s === 'ACTIVE') return this.activeState;
    if (s === 'SUSPENDED') return this.suspendedState;
    if (s === 'GRADUATED') return this.graduatedState;
    return this.activeState;
  }
}
