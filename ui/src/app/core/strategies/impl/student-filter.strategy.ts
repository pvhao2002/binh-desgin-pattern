import { Injectable } from '@angular/core';
import { Student } from '../../models/student.model';
import { FilterStrategy, StudentFilterCriteria } from '../filter.strategy';

@Injectable({ providedIn: 'root' })
export class StudentFilterStrategy implements FilterStrategy {
  filter(items: Student[], criteria: StudentFilterCriteria): Student[] {
    let result = items;
    if (criteria.classId != null && criteria.classId !== undefined) {
      result = result.filter(s => s.classId === criteria.classId);
    }
    if (criteria.searchText?.trim()) {
      const t = criteria.searchText.trim().toLowerCase();
      result = result.filter(s =>
        (s.fullName?.toLowerCase().includes(t)) ||
        (s.email?.toLowerCase().includes(t)) ||
        (s.className?.toLowerCase().includes(t))
      );
    }
    return result;
  }
}
