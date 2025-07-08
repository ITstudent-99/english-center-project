package com.example.backend.entity;
import com.example.backend.utils.Constants.DayOfWeekVN;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "class_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSchedule extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "class_code", referencedColumnName = "class_code")
    private ClassGroup classGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_room_name", referencedColumnName = "name")
    private ClassRoom classRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekVN dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
}
