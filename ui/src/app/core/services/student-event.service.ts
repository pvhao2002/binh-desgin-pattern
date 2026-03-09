import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export type StudentEventType = 'created' | 'updated' | 'deleted';

export interface StudentEvent {
  type: StudentEventType;
  studentId?: number;
}

@Injectable({ providedIn: 'root' })
export class StudentEventService {
  private readonly events = new Subject<StudentEvent>();

  readonly events$ = this.events.asObservable();

  notify(event: StudentEvent): void {
    this.events.next(event);
  }
}
