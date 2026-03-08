package com.db.service.factory.abstractfactory;

import com.db.service.repository.StudentRepository;
import com.db.service.service.StudentService;

/**
 * Abstract Factory: provides a family of related components for the Student module.
 */
public interface StudentModuleFactory {
    StudentService createService();
    StudentRepository createRepository();
}
