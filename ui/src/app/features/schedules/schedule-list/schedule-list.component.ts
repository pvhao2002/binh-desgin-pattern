import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ScheduleService } from '../../../core/services/schedule.service';
import { Schedule } from '../../../core/models/schedule.model';

@Component({
  selector: 'app-schedule-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './schedule-list.component.html',
  styleUrls: ['./schedule-list.component.css']
})
export class ScheduleListComponent implements OnInit {
  items = signal<Schedule[]>([]);
  loading = signal(true);
  constructor(private service: ScheduleService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
