import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ScoreService } from '../../../core/services/score.service';
import { StudentService } from '../../../core/services/student.service';
import { CourseClassService } from '../../../core/services/course-class.service';
import { ScoreRequest } from '../../../core/models/score.model';
import { Student } from '../../../core/models/student.model';
import { CourseClass } from '../../../core/models/course-class.model';

@Component({
  selector: 'app-score-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './score-form.component.html',
  styleUrls: ['./score-form.component.css']
})
export class ScoreFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(ScoreService);
  private studentService = inject(StudentService);
  private courseClassService = inject(CourseClassService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  students = signal<Student[]>([]);
  courseClasses = signal<CourseClass[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ studentId: [null as number | null, Validators.required], courseClassId: [null as number | null, Validators.required], continuousAssessmentScore: [null as number | null], finalExamScore: [null as number | null] });
    this.studentService.getAll().subscribe(list => this.students.set(list));
    this.courseClassService.getAll().subscribe(list => this.courseClasses.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ studentId: next.studentId, courseClassId: next.courseClassId, continuousAssessmentScore: next.continuousAssessmentScore, finalExamScore: next.finalExamScore }));
    }
  }
  onSubmit(): void {
    const cmd: ScoreRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/scores']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/scores']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
