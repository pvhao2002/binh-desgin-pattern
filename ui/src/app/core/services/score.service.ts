import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Score, ScoreRequest } from '../models/score.model';

@Injectable({ providedIn: 'root' })
export class ScoreService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Score[]> {
    return this.api.get<Score[]>('/scores');
  }

  getById(id: number): Observable<Score> {
    return this.api.get<Score>(`/scores/${id}`);
  }

  create(command: ScoreRequest): Observable<Score> {
    return this.api.post<Score>('/scores', command);
  }

  update(id: number, command: ScoreRequest): Observable<Score> {
    return this.api.put<Score>(`/scores/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/scores/${id}`);
  }
}
