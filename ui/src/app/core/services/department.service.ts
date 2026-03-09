import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Department, DepartmentRequest } from '../models/department.model';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Department[]> {
    return this.api.get<Department[]>('/departments');
  }

  getById(id: number): Observable<Department> {
    return this.api.get<Department>(`/departments/${id}`);
  }

  create(command: DepartmentRequest): Observable<Department> {
    return this.api.post<Department>('/departments', command);
  }

  update(id: number, command: DepartmentRequest): Observable<Department> {
    return this.api.put<Department>(`/departments/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/departments/${id}`);
  }
}
