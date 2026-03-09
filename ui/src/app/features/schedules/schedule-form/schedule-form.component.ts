import { Component, signal, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ScheduleService } from '../../../core/services/schedule.service';
import { CourseClassService } from '../../../core/services/course-class.service';
import { RoomService } from '../../../core/services/room.service';
import { ScheduleRequest } from '../../../core/models/schedule.model';
import { CourseClass } from '../../../core/models/course-class.model';
import { Room } from '../../../core/models/room.model';

@Component({
  selector: 'app-schedule-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './schedule-form.component.html',
  styleUrls: ['./schedule-form.component.css']
})
export class ScheduleFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(ScheduleService);
  private courseClassService = inject(CourseClassService);
  private roomService = inject(RoomService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = signal(false);
  id = signal<number | null>(null);
  courseClasses = signal<CourseClass[]>([]);
  rooms = signal<Room[]>([]);
  ngOnInit(): void {
    this.form = this.fb.group({ courseClassId: [null as number | null, Validators.required], roomId: [null as number | null, Validators.required], dayOfWeek: [1, Validators.required], periodStart: [1, Validators.required], periodEnd: [null as number | null], note: [null as string | null] });
    this.courseClassService.getAll().subscribe(list => this.courseClasses.set(list));
    this.roomService.getAll().subscribe(list => this.rooms.set(list));
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.isEdit.set(true);
      this.service.getById(id).subscribe(next => this.form.patchValue({ courseClassId: next.courseClassId, roomId: next.roomId, dayOfWeek: next.dayOfWeek, periodStart: next.periodStart, periodEnd: next.periodEnd, note: next.note }));
    }
  }
  onSubmit(): void {
    const cmd: ScheduleRequest = this.form.value;
    if (this.isEdit() && this.id() != null) {
      this.service.update(this.id()!, cmd).subscribe({ next: () => this.router.navigate(['/schedules']), error: e => alert(e?.error?.message ?? 'Update failed') });
    } else {
      this.service.create(cmd).subscribe({ next: () => this.router.navigate(['/schedules']), error: e => alert(e?.error?.message ?? 'Create failed') });
    }
  }
}
