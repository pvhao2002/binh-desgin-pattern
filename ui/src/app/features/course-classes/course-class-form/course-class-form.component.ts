import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CourseClassService } from '../../../core/services/course-class.service';
import { SubjectService } from '../../../core/services/subject.service';
import { SemesterService } from '../../../core/services/semester.service';
import { ClassService } from '../../../core/services/class.service';
import { LecturerService } from '../../../core/services/lecturer.service';
import { CourseClassRequest } from '../../../core/models/course-class.model';
import { Subject } from '../../../core/models/subject.model';
import { Semester } from '../../../core/models/semester.model';
import { ClassItem } from '../../../core/models/class.model';
import { Lecturer } from '../../../core/models/lecturer.model';

@Component({
  selector: 'app-course-class-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './course-class-form.component.html',
  styleUrls: ['./course-class-form.component.css']
})
export class CourseClassFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(CourseClassService);
  private subjectService = inject(SubjectService);
  private semesterService = inject(SemesterService);
  private classService = inject(ClassService);
  private lecturerService = inject(LecturerService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  subjects = signal<Subject[]>([]);
  semesters = signal<Semester[]>([]);
  classes = signal<ClassItem[]>([]);
  lecturers = signal<Lecturer[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ subjectId: [null as number | null, Validators.required], semesterId: [null as number | null, Validators.required], classId: [null as number | null, Validators.required], lecturerId: [null as number | null, Validators.required] });
    this.subjectService.getAll().subscribe(list => this.subjects.set(list));
    this.semesterService.getAll().subscribe(list => this.semesters.set(list));
    this.classService.getAll().subscribe(list => this.classes.set(list));
    this.lecturerService.getAll().subscribe(list => this.lecturers.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ subjectId: next.subjectId, semesterId: next.semesterId, classId: next.classId, lecturerId: next.lecturerId }));
    }
  }
  onSubmit(): void {
    const cmd: CourseClassRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/course-classes']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/course-classes']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
