package com.example.backend.mapper;

import com.example.backend.dto.ScheduleDTO;
import com.example.backend.entity.ClassSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IScheduleMapper {
    @Mappings({
            @Mapping(target = "dayOfWeek", source = "dayOfWeek"),
            @Mapping(target = "startTime", source = "startTime"),
            @Mapping(target = "endTime", source = "endTime"),
            @Mapping(target = "classRoomName", source = "classRoom.name")
    })
    ScheduleDTO toDTO(ClassSchedule schedule);
}
