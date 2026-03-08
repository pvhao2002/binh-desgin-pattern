package com.db.service.service;

import com.db.service.command.CreateRoomCommand;
import com.db.service.command.DeleteRoomCommand;
import com.db.service.command.UpdateRoomCommand;
import com.db.service.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse execute(CreateRoomCommand command);
    RoomResponse execute(UpdateRoomCommand command);
    void execute(DeleteRoomCommand command);
    List<RoomResponse> findAll();
    RoomResponse findById(Long id);
}
