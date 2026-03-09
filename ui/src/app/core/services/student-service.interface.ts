import { Observable } from 'rxjs';
import { Student, StudentRequest } from '../models/student.model';

export interface IStudentService {
  getAll(): Observable<Student[]>;
  getById(id: number): Observable<Student>;
  create(command: StudentRequest): Observable<Student>;
  update(id: number, command: StudentRequest): Observable<Student>;
  delete(id: number): Observable<void>;
  restore?(id: number): Observable<Student>;
  getState?(id: number): Observable<{ studentId: number; status: string; canRegisterCourse: boolean }>;
}
