import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CourseClassService } from '../../../core/services/course-class.service';
import { CourseClass } from '../../../core/models/course-class.model';

@Component({
  selector: 'app-course-class-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './course-class-list.component.html',
  styleUrls: ['./course-class-list.component.css']
})
export class CourseClassListComponent implements OnInit {
  items = signal<CourseClass[]>([]);
  loading = signal(true);
  constructor(private service: CourseClassService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
