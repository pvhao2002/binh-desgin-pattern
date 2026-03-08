package com.db.service.iterator;

import com.db.service.dto.response.StudentResponse;
import com.db.service.entity.Student;
import com.db.service.factory.StudentResponseFactory;
import com.db.service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class StudentIterator implements Iterator<StudentResponse> {

    private static final int PAGE_SIZE = 20;

    private final StudentRepository repository;
    private final StudentResponseFactory responseFactory;

    private int currentPage = 0;
    private Iterator<Student> currentPageIterator;
    private boolean exhausted;

    @Override
    public boolean hasNext() {
        ensureLoaded();
        return currentPageIterator != null && currentPageIterator.hasNext();
    }

    @Override
    public StudentResponse next() {
        if (!hasNext()) throw new NoSuchElementException();
        return responseFactory.createFromEntity(currentPageIterator.next());
    }

    private void ensureLoaded() {
        if (exhausted) return;
        if (currentPageIterator != null && currentPageIterator.hasNext()) return;
        List<Student> page = repository.findAll(PageRequest.of(currentPage, PAGE_SIZE)).getContent();
        if (page.isEmpty()) {
            exhausted = true;
            currentPageIterator = null;
            return;
        }
        currentPageIterator = page.iterator();
        currentPage++;
        if (page.size() < PAGE_SIZE) exhausted = true;
    }
}
