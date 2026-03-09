import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class ReportService {
  constructor(private api: ApiService) {}

  getDepartmentTreeReport(): Observable<string[]> {
    return this.api.get<string[]>('/reports/department-tree');
  }
}
