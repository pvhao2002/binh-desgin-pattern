package com.db.service.mediator;

import com.db.service.dto.response.ScoreResponse;

public interface StudentRegistrationMediator {
    ScoreResponse registerStudentToCourseClass(Long studentId, Long courseClassId);
}
