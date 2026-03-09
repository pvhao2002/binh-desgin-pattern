import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Room, RoomRequest } from '../models/room.model';

@Injectable({ providedIn: 'root' })
export class RoomService {
  constructor(private api: ApiService) {}

  getAll(): Observable<Room[]> {
    return this.api.get<Room[]>('/rooms');
  }

  getById(id: number): Observable<Room> {
    return this.api.get<Room>(`/rooms/${id}`);
  }

  create(command: RoomRequest): Observable<Room> {
    return this.api.post<Room>('/rooms', command);
  }

  update(id: number, command: RoomRequest): Observable<Room> {
    return this.api.put<Room>(`/rooms/${id}`, command);
  }

  delete(id: number): Observable<void> {
    return this.api.delete(`/rooms/${id}`);
  }
}
