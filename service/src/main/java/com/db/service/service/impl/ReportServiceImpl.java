package com.db.service.service.impl;

import com.db.service.entity.ClassEntity;
import com.db.service.entity.Department;
import com.db.service.entity.Student;
import com.db.service.repository.ClassRepository;
import com.db.service.repository.DepartmentRepository;
import com.db.service.repository.StudentRepository;
import com.db.service.service.ReportService;
import com.db.service.visitor.EntityVisitor;
import com.db.service.visitor.ReportExportVisitor;
import com.db.service.visitor.model.ClassNode;
import com.db.service.visitor.model.DepartmentNode;
import com.db.service.visitor.model.StudentLeaf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DepartmentRepository departmentRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<String> exportDepartmentTreeReport() {
        List<Department> departments = departmentRepository.findAll();
        ReportExportVisitor visitor = new ReportExportVisitor();
        for (Department d : departments) {
            DepartmentNode root = buildDepartmentNode(d);
            root.accept(visitor);
        }
        return visitor.getReportLines();
    }

    private DepartmentNode buildDepartmentNode(Department d) {
        DepartmentNode node = new DepartmentNode();
        node.setDepartmentId(d.getDepartmentId());
        node.setDepartmentName(d.getDepartmentName());
        List<ClassEntity> classes = classRepository.findAll().stream()
                .filter(c -> c.getDepartment().getDepartmentId().equals(d.getDepartmentId()))
                .toList();
        for (ClassEntity c : classes) {
            ClassNode cn = new ClassNode();
            cn.setClassId(c.getClassId());
            cn.setClassName(c.getClassName());
            cn.setCourseYear(c.getCourseYear());
            List<Student> students = studentRepository.findAll().stream()
                    .filter(s -> s.getClassEntity().getClassId().equals(c.getClassId()))
                    .toList();
            for (Student s : students) {
                StudentLeaf sl = new StudentLeaf();
                sl.setStudentId(s.getStudentId());
                sl.setFullName(s.getFullName());
                sl.setEmail(s.getEmail());
                cn.getStudents().add(sl);
            }
            node.getClasses().add(cn);
        }
        return node;
    }
}
