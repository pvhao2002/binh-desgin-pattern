package com.db.service.service.impl;

import com.db.service.composite.AcademicComponent;
import com.db.service.composite.ClassNode;
import com.db.service.composite.DepartmentNode;
import com.db.service.composite.StudentLeaf;
import com.db.service.entity.ClassEntity;
import com.db.service.entity.Department;
import com.db.service.entity.Student;
import com.db.service.repository.ClassRepository;
import com.db.service.repository.DepartmentRepository;
import com.db.service.repository.StudentRepository;
import com.db.service.service.AcademicTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicTreeServiceImpl implements AcademicTreeService {

    private final DepartmentRepository departmentRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AcademicComponent> buildDepartmentTree() {
        List<AcademicComponent> roots = new ArrayList<>();
        for (Department d : departmentRepository.findAll()) {
            DepartmentNode deptNode = new DepartmentNode();
            deptNode.setDepartmentId(d.getDepartmentId());
            deptNode.setDepartmentName(d.getDepartmentName());
            List<ClassEntity> classes = classRepository.findAll().stream()
                    .filter(c -> c.getDepartment().getDepartmentId().equals(d.getDepartmentId()))
                    .toList();
            for (ClassEntity c : classes) {
                ClassNode classNode = new ClassNode();
                classNode.setClassId(c.getClassId());
                classNode.setClassName(c.getClassName());
                classNode.setCourseYear(c.getCourseYear());
                List<Student> students = studentRepository.findAll().stream()
                        .filter(s -> s.getClassEntity().getClassId().equals(c.getClassId()))
                        .toList();
                for (Student s : students) {
                    StudentLeaf leaf = new StudentLeaf();
                    leaf.setStudentId(s.getStudentId());
                    leaf.setFullName(s.getFullName());
                    classNode.add(leaf);
                }
                deptNode.add(classNode);
            }
            roots.add(deptNode);
        }
        return roots;
    }
}
