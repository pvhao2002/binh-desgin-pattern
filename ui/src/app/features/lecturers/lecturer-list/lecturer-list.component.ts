import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LecturerService } from '../../../core/services/lecturer.service';
import { Lecturer } from '../../../core/models/lecturer.model';

@Component({
  selector: 'app-lecturer-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './lecturer-list.component.html',
  styleUrls: ['./lecturer-list.component.css']
})
export class LecturerListComponent implements OnInit {
  items = signal<Lecturer[]>([]);
  loading = signal(true);
  constructor(private service: LecturerService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
