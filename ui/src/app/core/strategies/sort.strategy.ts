import { Student } from '../models/student.model';

export type SortDirection = 'asc' | 'desc';

export interface SortStrategy {
  sort(items: Student[], key: keyof Student | string, direction: SortDirection): Student[];
}
