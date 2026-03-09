import { Injectable } from '@angular/core';
import { Observable, forkJoin, map } from 'rxjs';
import { DepartmentService } from './department.service';
import { ClassService } from './class.service';
import { StudentService } from './student.service';
import { TreeNode } from '../models/tree-node.model';

@Injectable({ providedIn: 'root' })
export class AcademicTreeService {
  constructor(
    private departmentService: DepartmentService,
    private classService: ClassService,
    private studentService: StudentService
  ) {}

  buildTree(): Observable<TreeNode[]> {
    return forkJoin({
      departments: this.departmentService.getAll(),
      classes: this.classService.getAll(),
      students: this.studentService.getAll()
    }).pipe(
      map(({ departments, classes, students }) => {
        const roots: TreeNode[] = [];
        for (const d of departments) {
          const deptNode: TreeNode = {
            id: d.departmentId,
            label: d.departmentName,
            type: 'department',
            children: [],
            expandable: true
          };
          const deptClasses = classes.filter(c => c.departmentId === d.departmentId);
          for (const c of deptClasses) {
            const classNode: TreeNode = {
              id: c.classId,
              label: `${c.className} (${c.courseYear})`,
              type: 'class',
              children: [],
              expandable: true
            };
            const classStudents = students.filter(s => s.classId === c.classId);
            for (const s of classStudents) {
              classNode.children.push({
                id: s.studentId,
                label: `${s.fullName} (${s.email ?? '-'})`,
                type: 'student',
                children: [],
                expandable: false
              });
            }
            deptNode.children.push(classNode);
          }
          roots.push(deptNode);
        }
        return roots;
      })
    );
  }
}
