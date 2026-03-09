import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClassService } from '../../../core/services/class.service';
import { DepartmentService } from '../../../core/services/department.service';
import { ClassRequest } from '../../../core/models/class.model';
import { Department } from '../../../core/models/department.model';

@Component({
  selector: 'app-class-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './class-form.component.html',
  styleUrls: ['./class-form.component.css']
})
export class ClassFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(ClassService);
  private deptService = inject(DepartmentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  departments = signal<Department[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ className: ['', Validators.required], courseYear: ['', Validators.required], departmentId: [null as number | null, Validators.required] });
    this.deptService.getAll().subscribe(list => this.departments.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ className: next.className, courseYear: next.courseYear, departmentId: next.departmentId }));
    }
  }
  onSubmit(): void {
    const cmd: ClassRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/classes']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/classes']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
