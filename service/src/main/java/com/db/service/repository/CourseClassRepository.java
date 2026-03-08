package com.db.service.repository;

import com.db.service.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {
}
