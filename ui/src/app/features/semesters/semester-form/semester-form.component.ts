import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SemesterService } from '../../../core/services/semester.service';
import { SemesterRequest } from '../../../core/models/semester.model';

@Component({
  selector: 'app-semester-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './semester-form.component.html',
  styleUrls: ['./semester-form.component.css']
})
export class SemesterFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(SemesterService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  ngOnInit(): void {
    this.form = this.fb.group({ semesterName: ['', Validators.required], academicYear: ['', Validators.required] });
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ semesterName: next.semesterName, academicYear: next.academicYear }));
    }
  }
  onSubmit(): void {
    const cmd: SemesterRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/semesters']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/semesters']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
