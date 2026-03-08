package com.db.service.command;

import com.db.service.dto.request.LecturerRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLecturerCommand {
    private Long id;
    private LecturerRequest request;
}
