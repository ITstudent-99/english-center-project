// ICourseMapper.java
package com.example.backend.mapper;

import com.example.backend.dto.CourseDTO;
import com.example.backend.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICourseMapper {
    CourseDTO toDTO(Course course);
}

