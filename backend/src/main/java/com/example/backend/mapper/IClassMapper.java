package com.example.backend.mapper;

import com.example.backend.dto.ClassGroupDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.ScheduleDTO;
import com.example.backend.entity.ClassGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IClassMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "classCode", source = "classCode"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "teacherName", expression = "java(classGroup.getTeacher().getName())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "learningMode", source = "learningMode"),
            @Mapping(target = "currentStudents", source = "currentStudents"),
            @Mapping(target = "maxStudents", source = "maxStudents"),
            @Mapping(target = "courses", ignore = true), // sẽ xử lý thủ công
            @Mapping(target = "schedules", ignore = true)     // sẽ xử lý thủ công
    })
    ClassGroupDTO toDTO(ClassGroup classGroup);

    @AfterMapping
    default void afterMapping(ClassGroup classGroup, @MappingTarget ClassGroupDTO dto) {
        // Map thủ công danh sách course → CourseDTO
        dto.setCourses(
                classGroup.getCourses().stream()
                        .map(course -> new CourseDTO(
                                course.getCourseCode(),
                                course.getName(),
                                course.getLevel(),
                                course.getDescription(),
                                course.getDuration(),
                                course.getTuitionFee()
                        ))
                        .collect(Collectors.toList())
        );

        // Map thủ công lịch học
        dto.setSchedules(
                classGroup.getSchedules().stream()
                        .map(schedule -> new ScheduleDTO(
                                schedule.getDayOfWeek(),
                                schedule.getStartTime(),
                                schedule.getEndTime(),
                                schedule.getClassRoom().getName()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
