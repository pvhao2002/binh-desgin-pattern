import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { StudentOverview } from '../models/student-overview.model';

@Injectable({ providedIn: 'root' })
export class StudentOverviewFacadeService {
  constructor(private api: ApiService) {}

  getOverview(studentId: number): Observable<StudentOverview> {
    return this.api.get<StudentOverview>(`/student-overview/${studentId}`);
  }
}
