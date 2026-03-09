export interface ValidationResult {
  valid: boolean;
  errors: string[];
}

export interface ValidationHandler {
  setNext(handler: ValidationHandler): ValidationHandler;
  validate(value: unknown): ValidationResult;
}
