package com.db.service.command;

import com.db.service.dto.request.DepartmentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDepartmentCommand {
    private Long id;
    private DepartmentRequest request;
}
