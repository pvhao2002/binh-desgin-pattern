package com.db.service.flyweight;

import com.db.service.entity.Department;
import com.db.service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class DepartmentFlyweightFactory {

    private final DepartmentRepository departmentRepository;
    private final Map<Long, Department> cache = new ConcurrentHashMap<>();

    public Department get(Long id) {
        return cache.computeIfAbsent(id, k -> departmentRepository.findById(k).map(this::copyForCache).orElse(null));
    }

    public void evict(Long id) {
        cache.remove(id);
    }

    public void evictAll() {
        cache.clear();
    }

    private Department copyForCache(Department d) {
        return Department.builder()
                .departmentId(d.getDepartmentId())
                .departmentName(d.getDepartmentName())
                .establishedDate(d.getEstablishedDate())
                .description(d.getDescription())
                .build();
    }
}
