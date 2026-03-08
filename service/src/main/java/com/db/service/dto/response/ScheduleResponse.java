package com.db.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponse {
    private Long scheduleId;
    private Long courseClassId;
    private String courseClassLabel;
    private Long roomId;
    private String roomCode;
    private Integer dayOfWeek;
    private Integer periodStart;
    private Integer periodEnd;
    private String note;
}
