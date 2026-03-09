import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Schedule, ScheduleRequest } from '../models/schedule.model';

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Schedule[]> {
    return this.api.get<Schedule[]>('/schedules');
  }

  getById(id: number): Observable<Schedule> {
    return this.api.get<Schedule>(`/schedules/${id}`);
  }

  create(command: ScheduleRequest): Observable<Schedule> {
    return this.api.post<Schedule>('/schedules', command);
  }

  update(id: number, command: ScheduleRequest): Observable<Schedule> {
    return this.api.put<Schedule>(`/schedules/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/schedules/${id}`);
  }
}
