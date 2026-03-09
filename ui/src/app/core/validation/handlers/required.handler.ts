import { ValidationHandler, ValidationResult } from '../validation-handler';

export class RequiredValidationHandler implements ValidationHandler {
  private nextHandler: ValidationHandler | null = null;
  constructor(private readonly fieldName: string) {}

  setNext(handler: ValidationHandler): ValidationHandler {
    this.nextHandler = handler;
    return handler;
  }

  validate(value: unknown): ValidationResult {
    const v = value as Record<string, unknown>;
    const val = v?.[this.fieldName];
    if (val === undefined || val === null || (typeof val === 'string' && val.trim() === '')) {
      return { valid: false, errors: [`${this.fieldName} is required`] };
    }
    if (this.nextHandler) return this.nextHandler.validate(value);
    return { valid: true, errors: [] };
  }
}
