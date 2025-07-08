package com.example.backend.dto.request;

import com.example.backend.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    private LocalTime startTime;
    private LocalTime endTime;
    private Constants.DayOfWeekVN dayOfWeek;
    private Long classroomId;
}
