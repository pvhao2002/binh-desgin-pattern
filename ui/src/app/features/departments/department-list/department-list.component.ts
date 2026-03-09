import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DepartmentService } from '../../../core/services/department.service';
import { Department } from '../../../core/models/department.model';

@Component({
  selector: 'app-department-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent implements OnInit {
  departments = signal<Department[]>([]);
  loading = signal(true);

  constructor(private service: DepartmentService) {}

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (list) => { this.departments.set(list); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }
}
