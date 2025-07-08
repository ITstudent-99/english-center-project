package com.example.backend.service;

import com.example.backend.dto.ClassGroupDTO;
import com.example.backend.dto.request.ClassRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.utils.Constants;

public interface IClassService {
    PageResponse<ClassGroupDTO> getAllClasses();
    PageResponse<ClassGroupDTO> getAllClassesByPaged(int pageIndex, int pageSize);
    PageResponse<ClassGroupDTO> filterClasses(
            int pageIndex, int pageSize,
            String name,
            String classCode,
            String teacherName,
            Constants.ClassStatus status,
            Constants.LearningMode learningMode,
            String courseName
    );

    ClassGroupDTO createClass(ClassRequest request);
    ClassGroupDTO updateClass(String classCode, ClassRequest request);
    void deleteClass(String classCode);
}
