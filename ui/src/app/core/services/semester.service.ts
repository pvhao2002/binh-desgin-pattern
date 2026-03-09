import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Semester, SemesterRequest } from '../models/semester.model';

@Injectable({ providedIn: 'root' })
export class SemesterService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Semester[]> {
    return this.api.get<Semester[]>('/semesters');
  }

  getById(id: number): Observable<Semester> {
    return this.api.get<Semester>(`/semesters/${id}`);
  }

  create(command: SemesterRequest): Observable<Semester> {
    return this.api.post<Semester>('/semesters', command);
  }

  update(id: number, command: SemesterRequest): Observable<Semester> {
    return this.api.put<Semester>(`/semesters/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/semesters/${id}`);
  }
}
