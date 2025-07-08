package com.example.backend.service.impl;

import com.example.backend.dto.ClassGroupDTO;
import com.example.backend.dto.ScheduleDTO;
import com.example.backend.dto.request.ClassRequest;
import com.example.backend.dto.request.ScheduleRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.entity.*;
import com.example.backend.mapper.IClassMapper;
import com.example.backend.repository.*;
import com.example.backend.service.IClassService;
import com.example.backend.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements IClassService {
    @Autowired
    private IClassRepository classRepository;

    @Autowired
    private IClassScheduleRepository classScheduleRepository;

    @Autowired
    private IClassMapper classMapper;

    @Autowired
    private IClassRoomRepository classroomRepository;

    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public PageResponse<ClassGroupDTO> getAllClasses() {
        List<ClassGroup> classGroups = classRepository.findAll();
        List<ClassGroupDTO> classGroupDTOS = classGroups.stream()
                .map(classMapper::toDTO)
                .toList();

        PageResponse<ClassGroupDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lấy danh sách lớp học thành công");
        response.setData(classGroupDTOS);
        response.setTotalPages(1);        // vì chưa phân trang
        response.setPageIndex(0);
        response.setPageSize(classGroupDTOS.size());
        response.setTotalElements(classGroupDTOS.size());

        return response;
    }

    @Override
    public PageResponse<ClassGroupDTO> getAllClassesByPaged(int pageIndex, int pageSize ){
        Page<ClassGroup> classGroups = classRepository.findAll(PageRequest.of(pageIndex, pageSize));
        List<ClassGroupDTO> classGroupDTOS = classGroups.getContent().stream()
                .map(classMapper::toDTO)
                .toList();

        PageResponse<ClassGroupDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lấy danh sách lớp học theo trang thành công");
        response.setData(classGroupDTOS);
        response.setTotalPages(classGroups.getTotalPages());
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        response.setTotalElements(classGroups.getTotalElements());
        return response;
    }

    @Override
    public PageResponse<ClassGroupDTO> filterClasses(
            int pageIndex,
            int pageSize,
            String classCode,
            String name,
            String teacherName,
            Constants.ClassStatus status,
            Constants.LearningMode learningMode,
            String courseName) {

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());

        Page<ClassGroup> classGroups = classRepository.filterClasses(
                classCode, name, teacherName, status, learningMode, courseName, pageRequest
        );

        List<ClassGroupDTO> classGroupDTOS = classGroups.getContent().stream()
                .map(classMapper::toDTO)
                .toList();

        PageResponse<ClassGroupDTO> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Lọc lớp học thành công");
        response.setData(classGroupDTOS);
        response.setTotalPages(classGroups.getTotalPages());
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        response.setTotalElements(classGroups.getTotalElements());

        return response;
    }

    @Override
    @Transactional
    public ClassGroupDTO createClass(ClassRequest request) {
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName(request.getName());
        classGroup.setStatus(request.getStatus());
        classGroup.setLearningMode(request.getLearningMode());
        classGroup.setMaxStudents(request.getMaxStudents());
        classGroup.setCurrentStudents(0);
        classGroup.setStudents(new ArrayList<>());

        if (request.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacherId()).orElse(null);
            classGroup.setTeacher(teacher);
        }

        if (request.getCourseIds() != null && !request.getCourseIds().isEmpty()) {
            List<Course> courses = courseRepository.findAllById(request.getCourseIds());
            classGroup.setCourses(courses);
        }

        // Lưu classGroup trước để lấy ID (dùng cho lịch học)
        classRepository.save(classGroup);

        // Tạo lịch học
        if (request.getSchedules() != null && !request.getSchedules().isEmpty()) {
            List<ClassSchedule> scheduleList = new ArrayList<>();

            for (ScheduleRequest sr : request.getSchedules()) {
                ClassRoom room = classroomRepository.findById(sr.getClassroomId()).orElse(null);

                if (room != null && sr.getDayOfWeek() != null && sr.getStartTime() != null && sr.getEndTime() != null ) {
                    ClassSchedule schedule = new ClassSchedule();
                    schedule.setClassGroup(classGroup);
                    schedule.setClassRoom(room);
                    schedule.setDayOfWeek(sr.getDayOfWeek());
                    schedule.setStartTime(sr.getStartTime());
                    schedule.setEndTime(sr.getEndTime());
                    scheduleList.add(schedule);
                }
            }

            classScheduleRepository.saveAll(scheduleList);
        }

        return null;
    }
}

