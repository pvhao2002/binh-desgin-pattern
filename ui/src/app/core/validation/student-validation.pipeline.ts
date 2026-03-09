import { Injectable } from '@angular/core';
import { ValidationHandler, ValidationResult } from './validation-handler';
import { RequiredValidationHandler } from './handlers/required.handler';
import { EmailFormatValidationHandler } from './handlers/email-format.handler';

@Injectable({ providedIn: 'root' })
export class StudentValidationPipeline {
  private chain: ValidationHandler;

  constructor() {
    const requiredFullName = new RequiredValidationHandler('fullName');
    const requiredClassId = new RequiredValidationHandler('classId');
    const emailFormat = new EmailFormatValidationHandler('email');
    requiredFullName.setNext(requiredClassId).setNext(emailFormat);
    this.chain = requiredFullName;
  }

  validate(formValue: unknown): ValidationResult {
    return this.chain.validate(formValue);
  }
}
