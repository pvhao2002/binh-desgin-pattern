import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DepartmentService } from '../../../core/services/department.service';
import { DepartmentRequest } from '../../../core/models/department.model';

@Component({
  selector: 'app-department-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './department-form.component.html',
  styleUrls: ['./department-form.component.css']
})
export class DepartmentFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(DepartmentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);

  ngOnInit(): void {
    this.form = this.fb.group({
      departmentName: ['', Validators.required],
      establishedDate: [null as string | null],
      description: [null as string | null]
    });
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe({
        next: (d) => this.form.patchValue({
          departmentName: d.departmentName,
          establishedDate: d.establishedDate ?? null,
          description: d.description ?? null
        })
      });
    }
  }

  onSubmit(): void {
    const v = this.form.value;
    const cmd: DepartmentRequest = {
      departmentName: v.departmentName,
      establishedDate: v.establishedDate || null,
      description: v.description || null
    };
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({
        next: () => this.router.navigate(['/departments']),
        error: (e) => alert(e?.error?.message ?? 'Update failed')
      });
    } else {
      this.service.create(cmd).subscribe({
        next: () => this.router.navigate(['/departments']),
        error: (e) => alert(e?.error?.message ?? 'Create failed')
      });
    }
  }
}
