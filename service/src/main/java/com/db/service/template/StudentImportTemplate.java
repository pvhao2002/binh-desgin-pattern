package com.db.service.template;

import com.db.service.entity.ClassEntity;
import com.db.service.entity.Student;
import com.db.service.repository.ClassRepository;
import com.db.service.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentImportTemplate extends AbstractDataImportTemplate {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentImportTemplate(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    protected List<String> readRaw(InputStream input) {
        List<String> lines = new ArrayList<>();
        try (var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) lines.add(line.trim());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read input", e);
        }
        return lines;
    }

    @Override
    protected void validate(List<String> raw) {
        for (int i = 0; i < raw.size(); i++) {
            String line = raw.get(i);
            if (!line.contains(",")) throw new RuntimeException("Invalid format at line " + (i + 1) + ": expected csv");
        }
    }

    @Override
    protected List<Student> mapToEntities(List<String> raw) {
        List<Student> result = new ArrayList<>();
        for (int i = 1; i < raw.size(); i++) {
            String line = raw.get(i);
            String[] parts = line.split(",", -1);
            if (parts.length < 3) continue;
            String fullName = parts[0].trim();
            String classIdStr = parts[1].trim();
            String email = parts.length > 2 ? parts[2].trim() : "";
            Long classId = Long.parseLong(classIdStr);
            ClassEntity clazz = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found: " + classId));
            Student s = Student.builder()
                    .fullName(fullName)
                    .email(email.isEmpty() ? null : email)
                    .classEntity(clazz)
                    .build();
            result.add(s);
        }
        return result;
    }

    @Override
    protected int save(List<?> entities) {
        int count = 0;
        for (Object e : entities) {
            studentRepository.save((Student) e);
            count++;
        }
        return count;
    }
}
