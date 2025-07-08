package com.example.backend.service;

import com.example.backend.dto.TeacherDTO;
import com.example.backend.dto.response.PageResponse;

public interface ITeacherService {
     PageResponse<TeacherDTO> getAllTeachers();
     PageResponse<TeacherDTO> getAllTeachersByPaged(int pageIndex, int pageSize);
     PageResponse<TeacherDTO> filterTeachers(int pageIndex, int pageSize, String teacherCode, String name, String email);
}
