import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SubjectService } from '../../../core/services/subject.service';
import { DepartmentService } from '../../../core/services/department.service';
import { SubjectRequest } from '../../../core/models/subject.model';
import { Department } from '../../../core/models/department.model';

@Component({
  selector: 'app-subject-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './subject-form.component.html',
  styleUrls: ['./subject-form.component.css']
})
export class SubjectFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(SubjectService);
  private deptService = inject(DepartmentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  departments = signal<Department[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ subjectName: ['', Validators.required], credits: [null as number | null], departmentId: [null as number | null, Validators.required], description: [null as string | null] });
    this.deptService.getAll().subscribe(list => this.departments.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ subjectName: next.subjectName, credits: next.credits, departmentId: next.departmentId, description: next.description }));
    }
  }
  onSubmit(): void {
    const cmd: SubjectRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/subjects']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/subjects']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
