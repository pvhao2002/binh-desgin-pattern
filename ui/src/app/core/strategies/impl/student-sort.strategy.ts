import { Injectable } from '@angular/core';
import { Student } from '../../models/student.model';
import { SortStrategy, SortDirection } from '../sort.strategy';

@Injectable({ providedIn: 'root' })
export class StudentSortStrategy implements SortStrategy {
  sort(items: Student[], key: keyof Student | string, direction: SortDirection): Student[] {
    const k = key as keyof Student;
    const cmp = direction === 'asc' ? 1 : -1;
    return [...items].sort((a, b) => {
      const va = a[k];
      const vb = b[k];
      if (va == null && vb == null) return 0;
      if (va == null) return cmp;
      if (vb == null) return -cmp;
      if (typeof va === 'string' && typeof vb === 'string') {
        return cmp * va.localeCompare(vb);
      }
      if (typeof va === 'number' && typeof vb === 'number') {
        return cmp * (va - vb);
      }
      return 0;
    });
  }
}
