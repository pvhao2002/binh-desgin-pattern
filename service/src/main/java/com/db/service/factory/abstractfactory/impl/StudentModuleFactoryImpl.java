package com.db.service.factory.abstractfactory.impl;

import com.db.service.factory.abstractfactory.StudentModuleFactory;
import com.db.service.repository.StudentRepository;
import com.db.service.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StudentModuleFactoryImpl implements StudentModuleFactory {

    private final ApplicationContext applicationContext;

    public StudentModuleFactoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public StudentService createService() {
        return applicationContext.getBean(StudentService.class);
    }

    @Override
    public StudentRepository createRepository() {
        return applicationContext.getBean(StudentRepository.class);
    }
}
