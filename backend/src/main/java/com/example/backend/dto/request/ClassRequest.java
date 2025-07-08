package com.example.backend.dto.request;

import com.example.backend.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    private String name;
    private Constants.ClassStatus status;        // ACTIVE, UPCOMING, COMPLETED, ...
    private Constants.LearningMode learningMode; // ONLINE, OFFLINE, HYBRID
    private int maxStudents;
    private Long classroomId;                    // có thể null nếu ONLINE
    private Long teacherId;
    private List<Long> courseIds;
    private List<ScheduleRequest> schedules;
}
