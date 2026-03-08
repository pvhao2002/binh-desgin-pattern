package com.db.service.bridge;

import com.db.service.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepositoryBridge {
    Optional<Student> findById(Long id);
    List<Student> findAll();
    Student save(Student student);
    void deleteById(Long id);
    boolean existsById(Long id);
}
