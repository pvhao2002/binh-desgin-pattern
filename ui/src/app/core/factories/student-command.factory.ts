import { Injectable } from '@angular/core';
import { CreateStudentCommand, UpdateStudentCommand } from '../commands/student.command';
import { StudentRequest } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class StudentCommandFactory {
  createFromFormValue(formValue: Record<string, unknown>): CreateStudentCommand {
    return {
      fullName: String(formValue['fullName'] ?? ''),
      dateOfBirth: (formValue['dateOfBirth'] as string) || null,
      gender: (formValue['gender'] as string) || null,
      address: (formValue['address'] as string) || null,
      phoneNumber: (formValue['phoneNumber'] as string) || null,
      email: (formValue['email'] as string) || null,
      classId: Number(formValue['classId']) || 0
    };
  }

  createUpdateCommand(id: number, formValue: Record<string, unknown>): UpdateStudentCommand {
    return {
      id,
      request: this.createFromFormValue(formValue)
    };
  }
}
