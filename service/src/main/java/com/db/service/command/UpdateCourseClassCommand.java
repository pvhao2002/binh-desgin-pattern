package com.db.service.command;

import com.db.service.dto.request.CourseClassRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseClassCommand {
    private Long id;
    private CourseClassRequest request;
}
