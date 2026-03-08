package com.db.service.iterator;

import com.db.service.dto.response.StudentResponse;
import com.db.service.factory.StudentResponseFactory;
import com.db.service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class StudentAggregateImpl implements StudentAggregate {

    private final StudentRepository repository;
    private final StudentResponseFactory responseFactory;

    @Override
    public Iterator<StudentResponse> iterator() {
        return new StudentIterator(repository, responseFactory);
    }
}
