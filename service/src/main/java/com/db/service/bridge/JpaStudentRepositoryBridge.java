package com.db.service.bridge;

import com.db.service.entity.Student;
import com.db.service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaStudentRepositoryBridge implements StudentRepositoryBridge {

    private final StudentRepository repository;

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
