package com.example.backend.dto;
import com.example.backend.utils.Constants.DayOfWeekVN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private DayOfWeekVN dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classRoomName;
}
