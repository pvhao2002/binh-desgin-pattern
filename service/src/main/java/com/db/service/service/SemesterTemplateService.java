package com.db.service.service;

import com.db.service.dto.response.SemesterResponse;
import com.db.service.prototype.SemesterTemplate;

public interface SemesterTemplateService {
    /**
     * Create a new semester by cloning the given template and persisting.
     */
    SemesterResponse createFromTemplate(SemesterTemplate template);
}
