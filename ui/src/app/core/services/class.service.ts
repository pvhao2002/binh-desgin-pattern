import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ClassItem, ClassRequest } from '../models/class.model';

@Injectable({ providedIn: 'root' })
export class ClassService {
  constructor(private api: ApiService) {}

  getAll(): Observable<ClassItem[]> {
    return this.api.get<ClassItem[]>('/classes');
  }

  getById(id: number): Observable<ClassItem> {
    return this.api.get<ClassItem>(`/classes/${id}`);
  }

  create(command: ClassRequest): Observable<ClassItem> {
    return this.api.post<ClassItem>('/classes', command);
  }

  update(id: number, command: ClassRequest): Observable<ClassItem> {
    return this.api.put<ClassItem>(`/classes/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/classes/${id}`);
  }
}
