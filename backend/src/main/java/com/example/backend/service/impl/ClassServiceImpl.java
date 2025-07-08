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
        classGroup.setClassCode(generateClassCode());
        classGroup.setName(request.getName());
        classGroup.setStatus(request.getStatus());
        classGroup.setLearningMode(request.getLearningMode());
        classGroup.setMaxStudents(request.getMaxStudents());
        classGroup.setCurrentStudents(0);
        classGroup.setStudents(new ArrayList<>());

        if (request.getTeacherCode() != null) {
            Teacher teacher = teacherRepository.findByTeacherCode(request.getTeacherCode());
            classGroup.setTeacher(teacher);
        }

        if (request.getCourseCodes() != null && !request.getCourseCodes().isEmpty()) {
            List<Course> courses = courseRepository.findByCourseCodeIn(request.getCourseCodes());
            classGroup.setCourses(courses);
        }

        // Lưu classGroup trước để lấy ID (dùng cho lịch học)
        classRepository.save(classGroup);

        // Tạo lịch học
        if (request.getSchedules() != null && !request.getSchedules().isEmpty()) {
            List<ClassSchedule> scheduleList = new ArrayList<>();

            for (ScheduleRequest sr : request.getSchedules()) {
                if (!validateSchedule(sr)) continue;

                ClassRoom room = classroomRepository.findByName(sr.getClassroomName());
                if (room == null) continue;

                ClassSchedule schedule = new ClassSchedule();
                schedule.setClassGroup(classGroup);
                schedule.setClassRoom(room);
                schedule.setDayOfWeek(sr.getDayOfWeek());
                schedule.setStartTime(sr.getStartTime());
                schedule.setEndTime(sr.getEndTime());
                scheduleList.add(schedule);
            }

            classScheduleRepository.saveAll(scheduleList);
            classGroup.setSchedules(scheduleList);
        }

        return classMapper.toDTO(classGroup);
    }

    @Override
    @Transactional
    public ClassGroupDTO updateClass(String classCode, ClassRequest request) {
        ClassGroup classGroup = classRepository.findByClassCode(classCode);
        if (classGroup == null) {
            throw new RuntimeException("Không tìm thấy lớp với mã: " + classCode);
        }

        if (request.getName() != null) {
            classGroup.setName(request.getName());
        }
        if (request.getStatus() != null) {
            classGroup.setStatus(request.getStatus());
        }
        if (request.getLearningMode() != null) {
            classGroup.setLearningMode(request.getLearningMode());
        }

        classGroup.setMaxStudents(request.getMaxStudents());

        if (request.getTeacherCode() != null) {
            Teacher teacher = teacherRepository.findByTeacherCode(request.getTeacherCode());
            classGroup.setTeacher(teacher);
        }

        if (request.getCourseCodes() != null && !request.getCourseCodes().isEmpty()) {
            List<Course> courses = courseRepository.findByCourseCodeIn(request.getCourseCodes());
            classGroup.setCourses(courses);
        }

        if (request.getSchedules() != null && !request.getSchedules().isEmpty()) {
            List<ClassSchedule> oldSchedules = classGroup.getSchedules();
            if (oldSchedules != null) {
                oldSchedules.clear(); // orphanRemoval = true sẽ xoá trên DB
            } else {
                oldSchedules = new ArrayList<>();
                classGroup.setSchedules(oldSchedules);
            }

            List<ClassSchedule> newSchedules = buildSchedules(request.getSchedules(), classGroup);
            oldSchedules.addAll(newSchedules);
        }

        classRepository.save(classGroup);
        return classMapper.toDTO(classGroup);
    }

    @Override
    @Transactional
    public void deleteClass(String classCode) {
        ClassGroup classGroup = classRepository.findByClassCode(classCode);
        if (classGroup == null) {
            throw new RuntimeException("Không tìm thấy lớp với mã: " + classCode);
        }

        // 🔌 Ngắt liên kết để tránh lỗi ràng buộc ở các bảng trung gian
        if (classGroup.getStudents() != null) {
            classGroup.getStudents().clear(); // xóa dữ liệu ở bảng class_students
        }

        if (classGroup.getCourses() != null) {
            classGroup.getCourses().clear(); // xóa dữ liệu ở bảng class_courses
        }

        // 🧹 orphanRemoval = true sẽ tự động xóa các bản ghi trong class_schedules
        if (classGroup.getSchedules() != null) {
            classGroup.getSchedules().clear(); // rõ ràng hơn để Hibernate xoá hết schedule
        }

        classRepository.delete(classGroup); // cuối cùng mới xóa class
    }


    private String generateClassCode() {
        Long maxId = classRepository.findMaxId();
        long nextNumber = (maxId != null ? maxId : 0) + 1;
        return String.format("CLS%03d", nextNumber);
    }

    private boolean validateSchedule(ScheduleRequest sr) {
        return sr != null &&
                sr.getClassroomName() != null &&
                sr.getDayOfWeek() != null &&
                sr.getStartTime() != null &&
                sr.getEndTime() != null;
    }

    private List<ClassSchedule> buildSchedules(List<ScheduleRequest> scheduleRequests, ClassGroup classGroup) {
        List<ClassSchedule> schedules = new ArrayList<>();
        for (ScheduleRequest sr : scheduleRequests) {
            if (!validateSchedule(sr)) continue;
            ClassRoom room = classroomRepository.findByName(sr.getClassroomName());
            if (room == null) continue;

            ClassSchedule schedule = new ClassSchedule();
            schedule.setClassGroup(classGroup);
            schedule.setClassRoom(room);
            schedule.setDayOfWeek(sr.getDayOfWeek());
            schedule.setStartTime(sr.getStartTime());
            schedule.setEndTime(sr.getEndTime());
            schedules.add(schedule);
        }
        return schedules;
    }
}

