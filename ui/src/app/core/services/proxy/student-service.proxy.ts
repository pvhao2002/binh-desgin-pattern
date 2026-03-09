import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { StudentService } from '../student.service';
import { IStudentService } from '../student-service.interface';
import { Student, StudentRequest } from '../../models/student.model';

@Injectable({ providedIn: 'root' })
export class StudentServiceProxy implements IStudentService {
  private readonly cache = new Map<number, Student>();

  constructor(private readonly real: StudentService) {}

  getAll(): Observable<Student[]> {
    return this.real.getAll();
  }

  getById(id: number): Observable<Student> {
    const cached = this.cache.get(id);
    if (cached) return of(cached);
    return this.real.getById(id).pipe(tap(s => this.cache.set(id, s)));
  }

  create(command: StudentRequest): Observable<Student> {
    return this.real.create(command);
  }

  update(id: number, command: StudentRequest): Observable<Student> {
    this.cache.delete(id);
    return this.real.update(id, command);
  }

  delete(id: number): Observable<void> {
    this.cache.delete(id);
    return this.real.delete(id);
  }

  restore(id: number): Observable<Student> {
    this.cache.delete(id);
    return this.real.restore(id);
  }

  getState(id: number) {
    return this.real.getState(id);
  }
}
