import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Lecturer, LecturerRequest } from '../models/lecturer.model';

@Injectable({ providedIn: 'root' })
export class LecturerService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Lecturer[]> {
    return this.api.get<Lecturer[]>('/lecturers');
  }

  getById(id: number): Observable<Lecturer> {
    return this.api.get<Lecturer>(`/lecturers/${id}`);
  }

  create(command: LecturerRequest): Observable<Lecturer> {
    return this.api.post<Lecturer>('/lecturers', command);
  }

  update(id: number, command: LecturerRequest): Observable<Lecturer> {
    return this.api.put<Lecturer>(`/lecturers/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/lecturers/${id}`);
  }
}
