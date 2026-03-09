import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RoomService } from '../../../core/services/room.service';
import { Room } from '../../../core/models/room.model';

@Component({
  selector: 'app-room-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {
  items = signal<Room[]>([]);
  loading = signal(true);
  constructor(private service: RoomService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
