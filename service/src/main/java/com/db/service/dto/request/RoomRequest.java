package com.db.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private String roomCode;
    private String building;
    private Integer capacity;
    private String roomType;
    private String description;
}
