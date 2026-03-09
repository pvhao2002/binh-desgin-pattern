import { Student } from '../models/student.model';

export interface StudentFilterCriteria {
  classId?: number | null;
  searchText?: string;
}

export interface FilterStrategy {
  filter(items: Student[], criteria: StudentFilterCriteria): Student[];
}
