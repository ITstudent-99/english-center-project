package com.example.backend.mapper;

import com.example.backend.dto.TeacherDTO;
import com.example.backend.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ITeacherMapper {
    @Mappings({
            @Mapping(target = "teacherCode", source = "teacherCode"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "phone", source = "phone"),
            @Mapping(target = "specialization", source = "specialization")
    })
    TeacherDTO toDTO(Teacher teacher);
}
