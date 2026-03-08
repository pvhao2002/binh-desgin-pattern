package com.db.service.service;

import com.db.service.command.CreateScheduleCommand;
import com.db.service.command.DeleteScheduleCommand;
import com.db.service.command.UpdateScheduleCommand;
import com.db.service.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse execute(CreateScheduleCommand command);
    ScheduleResponse execute(UpdateScheduleCommand command);
    void execute(DeleteScheduleCommand command);
    List<ScheduleResponse> findAll();
    ScheduleResponse findById(Long id);
}
