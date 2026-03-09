import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudentService } from './student.service';
import { Student } from '../models/student.model';
import {
  CreateStudentCommand,
  UpdateStudentCommand,
  DeleteStudentCommand
} from '../commands/student.command';

@Injectable({ providedIn: 'root' })
export class StudentCommandExecutor {
  constructor(private studentService: StudentService) {}

  executeCreate(command: CreateStudentCommand): Observable<Student> {
    return this.studentService.create(command);
  }

  executeUpdate(command: UpdateStudentCommand): Observable<Student> {
    return this.studentService.update(command.id, command.request);
  }

  executeDelete(command: DeleteStudentCommand): Observable<void> {
    return this.studentService.delete(command.id);
  }
}
