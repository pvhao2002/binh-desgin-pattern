import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { StudentService } from '../student.service';
import { IStudentService } from '../student-service.interface';
import { Student, StudentRequest } from '../../models/student.model';

@Injectable({ providedIn: 'root' })
export class LoggingStudentServiceDecorator implements IStudentService {
  constructor(private readonly real: StudentService) {}

  getAll(): Observable<Student[]> {
    console.debug('[LoggingStudentService] getAll');
    return this.real.getAll().pipe(tap(() => console.debug('[LoggingStudentService] getAll done')));
  }

  getById(id: number): Observable<Student> {
    console.debug('[LoggingStudentService] getById', id);
    return this.real.getById(id).pipe(tap(() => console.debug('[LoggingStudentService] getById done', id)));
  }

  create(command: StudentRequest): Observable<Student> {
    console.debug('[LoggingStudentService] create');
    return this.real.create(command).pipe(tap(s => console.debug('[LoggingStudentService] created', s.studentId)));
  }

  update(id: number, command: StudentRequest): Observable<Student> {
    console.debug('[LoggingStudentService] update', id);
    return this.real.update(id, command).pipe(tap(() => console.debug('[LoggingStudentService] update done', id)));
  }

  delete(id: number): Observable<void> {
    console.debug('[LoggingStudentService] delete', id);
    return this.real.delete(id).pipe(tap(() => console.debug('[LoggingStudentService] delete done', id)));
  }

  restore(id: number): Observable<Student> {
    console.debug('[LoggingStudentService] restore', id);
    return this.real.restore(id).pipe(tap(() => console.debug('[LoggingStudentService] restore done', id)));
  }

  getState(id: number) {
    return this.real.getState(id);
  }
}
