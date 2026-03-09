import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Student, StudentRequest } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class StudentService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Student[]> {
    return this.api.get<Student[]>('/students');
  }

  getById(id: number): Observable<Student> {
    return this.api.get<Student>(`/students/${id}`);
  }

  create(command: StudentRequest): Observable<Student> {
    return this.api.post<Student>('/students', command);
  }

  update(id: number, command: StudentRequest): Observable<Student> {
    return this.api.put<Student>(`/students/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/students/${id}`);
  }

  restore(id: number): Observable<Student> {
    return this.api.post<Student>(`/students/${id}/restore`, {});
  }

  getState(id: number): Observable<{ studentId: number; status: string; canRegisterCourse: boolean }> {
    return this.api.get<{ studentId: number; status: string; canRegisterCourse: boolean }>(`/students/${id}/state`);
  }
}
