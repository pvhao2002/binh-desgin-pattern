import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BaseImportFlow, ImportResult } from './base-import.flow';
import { StudentRequest } from '../models/student.model';
import { StudentService } from '../services/student.service';

@Injectable({ providedIn: 'root' })
export class StudentImportFlow extends BaseImportFlow<StudentRequest> {

  constructor(private studentService: StudentService) {
    super();
  }

  override readFile(file: File): Observable<string> {
    return new Observable(observer => {
      const reader = new FileReader();
      reader.onload = () => { observer.next(reader.result as string); observer.complete(); };
      reader.onerror = () => observer.error(reader.error);
      reader.readAsText(file, 'UTF-8');
    });
  }

  override validate(raw: string): string[] {
    const errors: string[] = [];
    const lines = raw.split(/\r?\n/).map(l => l.trim()).filter(Boolean);
    if (lines.length < 2) errors.push('File must have header and at least one data row');
    return errors;
  }

  override mapToModel(raw: string): StudentRequest[] {
    const lines = raw.split(/\r?\n/).map(l => l.trim()).filter(Boolean);
    const result: StudentRequest[] = [];
    for (let i = 1; i < lines.length; i++) {
      const parts = lines[i].split(',').map(p => p.trim());
      if (parts.length >= 2) {
        result.push({
          fullName: parts[0] ?? '',
          dateOfBirth: null,
          gender: null,
          address: null,
          phoneNumber: null,
          email: parts.length > 2 ? parts[2] : null,
          classId: Number(parts[1]) || 0
        });
      }
    }
    return result;
  }

  override submit(items: StudentRequest[]): Observable<ImportResult> {
    if (items.length === 0) return of({ success: true, count: 0, errors: [] });
    let done = 0;
    const errors: string[] = [];
    return new Observable(observer => {
      const next = (index: number) => {
        if (index >= items.length) {
          observer.next({ success: errors.length === 0, count: done, errors });
          observer.complete();
          return;
        }
        this.studentService.create(items[index]).subscribe({
          next: () => { done++; next(index + 1); },
          error: (e) => { errors.push(`Row ${index + 2}: ${e?.error?.message ?? 'Failed'}`); next(index + 1); }
        });
      };
      next(0);
    });
  }
}
