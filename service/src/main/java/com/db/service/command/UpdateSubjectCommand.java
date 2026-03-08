package com.db.service.command;

import com.db.service.dto.request.SubjectRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubjectCommand {
    private Long id;
    private SubjectRequest request;
}
