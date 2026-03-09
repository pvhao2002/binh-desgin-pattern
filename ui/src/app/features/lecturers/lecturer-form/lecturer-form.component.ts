import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LecturerService } from '../../../core/services/lecturer.service';
import { DepartmentService } from '../../../core/services/department.service';
import { LecturerRequest } from '../../../core/models/lecturer.model';
import { Department } from '../../../core/models/department.model';

@Component({
  selector: 'app-lecturer-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './lecturer-form.component.html',
  styleUrls: ['./lecturer-form.component.css']
})
export class LecturerFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(LecturerService);
  private deptService = inject(DepartmentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  departments = signal<Department[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ lecturerName: ['', Validators.required], academicDegree: [null], email: [null], phoneNumber: [null], departmentId: [null as number | null, Validators.required] });
    this.deptService.getAll().subscribe(list => this.departments.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ lecturerName: next.lecturerName, academicDegree: next.academicDegree, email: next.email, phoneNumber: next.phoneNumber, departmentId: next.departmentId }));
    }
  }
  onSubmit(): void {
    const cmd: LecturerRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/lecturers']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/lecturers']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
