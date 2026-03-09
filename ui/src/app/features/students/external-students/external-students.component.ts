import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExternalApiStudentAdapter } from '../../../core/adapters/external-student.adapter';
import { ExternalStudentDto } from '../../../core/adapters/external-student.adapter';

@Component({
  selector: 'app-external-students',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './external-students.component.html',
  styleUrls: ['./external-students.component.css']
})
export class ExternalStudentsComponent implements OnInit {
  private adapter = inject(ExternalApiStudentAdapter);

  students = signal<ExternalStudentDto[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.adapter.getStudents().subscribe({
      next: (list) => { this.students.set(list); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }
}
