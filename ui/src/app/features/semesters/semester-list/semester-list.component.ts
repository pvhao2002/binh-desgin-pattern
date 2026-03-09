import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SemesterService } from '../../../core/services/semester.service';
import { Semester } from '../../../core/models/semester.model';

@Component({
  selector: 'app-semester-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './semester-list.component.html',
  styleUrls: ['./semester-list.component.css']
})
export class SemesterListComponent implements OnInit {
  items = signal<Semester[]>([]);
  loading = signal(true);
  constructor(private service: SemesterService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
