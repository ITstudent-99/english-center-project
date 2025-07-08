package com.example.backend.api;

import com.example.backend.dto.TeacherDTO;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherApi {
    @Autowired
    private ITeacherService teacherService;

    @GetMapping("/all")
    public ResponseEntity<PageResponse<TeacherDTO>> getAllTeachers() {
        PageResponse<TeacherDTO> response = teacherService.getAllTeachers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/paged")
    public ResponseEntity<PageResponse<TeacherDTO>> getAllTeachersByPaged(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        PageResponse<TeacherDTO> response = teacherService.getAllTeachersByPaged(pageIndex, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponse<TeacherDTO>> filterTeacher(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String teacherCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ){
            PageResponse<TeacherDTO> response = teacherService.filterTeachers(pageIndex, pageSize, teacherCode,name, email);
            return ResponseEntity.ok(response);
    }
}
