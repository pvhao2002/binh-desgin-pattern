package com.db.service.command;

import com.db.service.dto.request.ScheduleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleCommand {
    private ScheduleRequest request;
}
