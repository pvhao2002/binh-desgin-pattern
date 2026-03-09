import { Component, signal, OnInit, inject, computed } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { StudentOverviewFacadeService } from '../../../core/services/student-overview.facade';
import { StudentService } from '../../../core/services/student.service';
import { StudentStateContextService } from '../../../core/state/student-state.context';
import { StudentState } from '../../../core/state/student-state';
import { StudentOverview } from '../../../core/models/student-overview.model';

@Component({
  selector: 'app-student-overview',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './student-overview.component.html',
  styleUrls: ['./student-overview.component.css']
})
export class StudentOverviewComponent implements OnInit {
  private facade = inject(StudentOverviewFacadeService);
  private studentService = inject(StudentService);
  private stateContext = inject(StudentStateContextService);
  private route = inject(ActivatedRoute);

  overview = signal<StudentOverview | null>(null);
  stateInfo = signal<{ status: string; canRegisterCourse: boolean } | null>(null);
  currentState = signal<StudentState | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(id)) {
      this.error.set('Invalid student id');
      this.loading.set(false);
      return;
    }
    this.facade.getOverview(id).subscribe({
      next: (data) => { this.overview.set(data); this.loading.set(false); },
      error: (e) => { this.error.set(e?.error?.message ?? 'Failed to load'); this.loading.set(false); }
    });
    this.studentService.getState(id).subscribe({
      next: (res) => {
        this.stateInfo.set({ status: res.status, canRegisterCourse: res.canRegisterCourse });
        this.currentState.set(this.stateContext.getStateForStatus(res.status));
      }
    });
  }
}
