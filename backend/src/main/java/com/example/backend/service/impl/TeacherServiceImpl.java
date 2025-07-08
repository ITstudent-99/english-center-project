package com.example.backend.service.impl;

import com.example.backend.dto.TeacherDTO;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.entity.Teacher;
import com.example.backend.mapper.ITeacherMapper;
import com.example.backend.repository.ITeacherRepository;
import com.example.backend.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private ITeacherMapper teacherMapper;

    @Override
    public PageResponse<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOS = teachers.stream().map(teacherMapper::toDTO).toList();

        PageResponse<TeacherDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lay danh sach giao vien thanh cong");
        response.setData(teacherDTOS);
        response.setTotalPages(1);
        response.setPageIndex(0);
        response.setPageSize(teacherDTOS.size());
        response.setTotalElements(teacherDTOS.size());

        return response;
    }

    @Override
    public PageResponse<TeacherDTO> getAllTeachersByPaged(int pageIndex, int pageSize ){
        Page<Teacher> teachers = teacherRepository.findAll(PageRequest.of(pageIndex, pageSize));
        List<TeacherDTO> teacherDTOS = teachers.getContent().stream().map(teacherMapper::toDTO).toList();
        PageResponse<TeacherDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lay danh sach giao vien thanh cong");
        response.setData(teacherDTOS);
        response.setTotalPages(teachers.getTotalPages());
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        response.setTotalElements(teachers.getTotalElements());
        return response;
    }

    @Override
    public PageResponse<TeacherDTO> filterTeachers(int pageIndex,
                                                   int pageSize,
                                                   String teacherCode, String name, String email){
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        Page<Teacher> teachers = teacherRepository.filterTeachers(pageRequest, teacherCode, name, email);
        List<TeacherDTO> teacherDTOS = teachers.getContent().stream().map(teacherMapper::toDTO).toList();
        PageResponse<TeacherDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lay danh sach giao vien thanh cong");
        response.setData(teacherDTOS);
        response.setTotalPages(teachers.getTotalPages());
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        response.setTotalElements(teachers.getTotalElements());
        return response;
    }
}

