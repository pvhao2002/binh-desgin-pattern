import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from '../services/api.service';

export interface ExternalStudentDto {
  studentId: number;
  fullName: string;
  email: string;
}

export interface ExternalStudentSource {
  getStudents(): Observable<ExternalStudentDto[]>;
}

@Injectable({ providedIn: 'root' })
export class ExternalApiStudentAdapter implements ExternalStudentSource {
  constructor(private api: ApiService) {}

  getStudents(): Observable<ExternalStudentDto[]> {
    return this.api.get<ExternalStudentDto[]>('/sync/external-students').pipe(
      map(list => list ?? [])
    );
  }
}
