import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { CourseClass, CourseClassRequest } from '../models/course-class.model';

@Injectable({ providedIn: 'root' })
export class CourseClassService {
  constructor(private api: ApiService) {}

  getAll(): Observable<CourseClass[]> {
    return this.api.get<CourseClass[]>('/course-classes');
  }

  getById(id: number): Observable<CourseClass> {
    return this.api.get<CourseClass>(`/course-classes/${id}`);
  }

  create(command: CourseClassRequest): Observable<CourseClass> {
    return this.api.post<CourseClass>('/course-classes', command);
  }

  update(id: number, command: CourseClassRequest): Observable<CourseClass> {
    return this.api.put<CourseClass>(`/course-classes/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/course-classes/${id}`);
  }
}
