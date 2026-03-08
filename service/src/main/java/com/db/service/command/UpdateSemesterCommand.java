package com.db.service.command;

import com.db.service.dto.request.SemesterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSemesterCommand {
    private Long id;
    private SemesterRequest request;
}
