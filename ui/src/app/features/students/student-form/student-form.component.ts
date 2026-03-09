import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StudentServiceProxy } from '../../../core/services/proxy/student-service.proxy';
import { StudentCommandExecutor } from '../../../core/services/student-command.executor';
import { StudentEventService } from '../../../core/services/student-event.service';
import { StudentValidationPipeline } from '../../../core/validation/student-validation.pipeline';
import { StudentCommandFactory } from '../../../core/factories/student-command.factory';
import { ClassService } from '../../../core/services/class.service';
import { ClassItem } from '../../../core/models/class.model';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private studentService = inject(StudentServiceProxy);
  private commandExecutor = inject(StudentCommandExecutor);
  private commandFactory = inject(StudentCommandFactory);
  private studentEvents = inject(StudentEventService);
  private validationPipeline = inject(StudentValidationPipeline);
  private classService = inject(ClassService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  classes = signal<ClassItem[]>([]);

  ngOnInit(): void {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      dateOfBirth: [null as string | null],
      gender: [null as string | null],
      address: [null as string | null],
      phoneNumber: [null as string | null],
      email: [null as string | null],
      classId: [null as number | null, Validators.required]
    });
    this.classService.getAll().subscribe(list => this.classes.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.studentService.getById(id).subscribe({
        next: (s) => this.form.patchValue({
          fullName: s.fullName,
          dateOfBirth: s.dateOfBirth ?? null,
          gender: s.gender ?? null,
          address: s.address ?? null,
          phoneNumber: s.phoneNumber ?? null,
          email: s.email ?? null,
          classId: s.classId
        })
      });
    }
  }

  onSubmit(): void {
    const v = this.form.value;
    const result = this.validationPipeline.validate(v);
    if (!result.valid) {
      alert(result.errors.join('\n'));
      return;
    }
    if (this.isEdit() && this.id() != null) {
      const command = this.commandFactory.createUpdateCommand(this.id()!, v);
      this.commandExecutor.executeUpdate(command).subscribe({
        next: (s) => { this.studentEvents.notify({ type: 'updated', studentId: s.studentId }); this.router.navigate(['/students']); },
        error: (e) => alert(e?.error?.message ?? 'Update failed')
      });
    } else {
      const createCmd = this.commandFactory.createFromFormValue(v);
      this.commandExecutor.executeCreate(createCmd).subscribe({
        next: (s) => { this.studentEvents.notify({ type: 'created', studentId: s.studentId }); this.router.navigate(['/students']); },
        error: (e) => alert(e?.error?.message ?? 'Create failed')
      });
    }
  }

  onRestore(): void {
    const id = this.id();
    if (id == null) return;
    if (!confirm('Restore this student to the previous saved version?')) return;
    this.studentService.restore(id).subscribe({
      next: (s) => {
        this.form.patchValue({
          fullName: s.fullName,
          dateOfBirth: s.dateOfBirth ?? null,
          gender: s.gender ?? null,
          address: s.address ?? null,
          phoneNumber: s.phoneNumber ?? null,
          email: s.email ?? null,
          classId: s.classId
        });
        this.studentEvents.notify({ type: 'updated', studentId: s.studentId });
      },
      error: (e) => alert(e?.error?.message ?? 'Restore failed')
    });
  }
}
