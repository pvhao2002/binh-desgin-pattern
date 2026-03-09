import { StudentRequest } from '../models/student.model';

export type CreateStudentCommand = StudentRequest;

export interface UpdateStudentCommand {
  id: number;
  request: StudentRequest;
}

export interface DeleteStudentCommand {
  id: number;
}

export type StudentCommand = 
  | { type: 'create'; payload: CreateStudentCommand }
  | { type: 'update'; payload: UpdateStudentCommand }
  | { type: 'delete'; payload: DeleteStudentCommand };
