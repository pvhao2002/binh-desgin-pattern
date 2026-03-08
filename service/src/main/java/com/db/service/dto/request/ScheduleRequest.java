package com.db.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequest {
    private Long courseClassId;
    private Long roomId;
    private Integer dayOfWeek;
    private Integer periodStart;
    private Integer periodEnd;
    private String note;
}
