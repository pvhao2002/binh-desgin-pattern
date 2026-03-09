import { Component, signal, OnInit, computed, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LoggingStudentServiceDecorator } from '../../../core/services/decorators/logging-student.service';
import { ClassService } from '../../../core/services/class.service';
import { Student } from '../../../core/models/student.model';
import { ClassItem } from '../../../core/models/class.model';
import { StudentFilterStrategy } from '../../../core/strategies/impl/student-filter.strategy';
import { StudentSortStrategy } from '../../../core/strategies/impl/student-sort.strategy';
import { FilterStrategy, StudentFilterCriteria } from '../../../core/strategies/filter.strategy';
import { SortStrategy, SortDirection } from '../../../core/strategies/sort.strategy';
import { StudentEventService } from '../../../core/services/student-event.service';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  private service = inject(LoggingStudentServiceDecorator);
  private classService = inject(ClassService);
  private filterStrategy: FilterStrategy = inject(StudentFilterStrategy);
  private sortStrategy: SortStrategy = inject(StudentSortStrategy);
  private studentEvents = inject(StudentEventService);

  rawStudents = signal<Student[]>([]);
  loading = signal(true);
  classes = signal<ClassItem[]>([]);
  filterClassId = signal<number | null>(null);
  searchText = signal('');
  sortKey = signal<keyof Student | string>('fullName');
  sortDirection = signal<SortDirection>('asc');

  filterCriteria = computed<StudentFilterCriteria>(() => ({
    classId: this.filterClassId(),
    searchText: this.searchText()
  }));

  displayedStudents = computed(() => {
    const raw = this.rawStudents();
    const criteria = this.filterCriteria();
    const filtered = this.filterStrategy.filter(raw, criteria);
    return this.sortStrategy.sort(filtered, this.sortKey(), this.sortDirection());
  });

  loadStudents(): void {
    this.loading.set(true);
    this.service.getAll().subscribe({
      next: (list) => { this.rawStudents.set(list); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  ngOnInit(): void {
    this.loadStudents();
    this.classService.getAll().subscribe(list => this.classes.set(list));
    this.studentEvents.events$.subscribe(() => this.loadStudents());
  }

  onFilterClassChange(e: Event): void {
    const v = (e.target as HTMLSelectElement).value;
    this.filterClassId.set(v === '' ? null : Number(v));
  }

  onSearchInput(e: Event): void {
    this.searchText.set((e.target as HTMLInputElement).value);
  }

  onSortKeyChange(e: Event): void {
    this.sortKey.set((e.target as HTMLSelectElement).value as keyof Student);
  }

  toggleSortDirection(): void {
    this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
  }
}
