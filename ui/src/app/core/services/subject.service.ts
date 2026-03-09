import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Subject, SubjectRequest } from '../models/subject.model';

@Injectable({ providedIn: 'root' })
export class SubjectService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Subject[]> {
    return this.api.get<Subject[]>('/subjects');
  }

  getById(id: number): Observable<Subject> {
    return this.api.get<Subject>(`/subjects/${id}`);
  }

  create(command: SubjectRequest): Observable<Subject> {
    return this.api.post<Subject>('/subjects', command);
  }

  update(id: number, command: SubjectRequest): Observable<Subject> {
    return this.api.put<Subject>(`/subjects/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/subjects/${id}`);
  }
}
