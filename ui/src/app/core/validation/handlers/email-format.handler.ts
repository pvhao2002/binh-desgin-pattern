import { ValidationHandler, ValidationResult } from '../validation-handler';

const EMAIL_REG = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export class EmailFormatValidationHandler implements ValidationHandler {
  private nextHandler: ValidationHandler | null = null;
  constructor(private readonly fieldName: string = 'email') {}

  setNext(handler: ValidationHandler): ValidationHandler {
    this.nextHandler = handler;
    return handler;
  }

  validate(value: unknown): ValidationResult {
    const v = value as Record<string, unknown>;
    const val = v?.[this.fieldName];
    if (val != null && typeof val === 'string' && val.trim() !== '' && !EMAIL_REG.test(val)) {
      return { valid: false, errors: [`${this.fieldName} must be a valid email`] };
    }
    if (this.nextHandler) return this.nextHandler.validate(value);
    return { valid: true, errors: [] };
  }
}
