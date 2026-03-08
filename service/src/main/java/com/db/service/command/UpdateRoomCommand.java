package com.db.service.command;

import com.db.service.dto.request.RoomRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomCommand {
    private Long id;
    private RoomRequest request;
}
