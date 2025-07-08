package com.example.backend.dto;

import com.example.backend.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupDTO {
    private Long id;
    private String classCode;
    private String name;               // Tên lớp học
    private String teacherName;       // Tên giáo viên
    private List<ScheduleDTO> schedules;         // Danh sách lịch học (đã chuyển thành DTO)
    private Constants.ClassStatus status;            // Trạng thái lớp học (ACTIVE, UPCOMING, ...)
    private Constants.LearningMode learningMode;      // Hình thức học: ONLINE, OFFLINE, HYBRID
    private int currentStudents;      // Số lượng học sinh hiện tại
    private int maxStudents;          // Số lượng tối đa
    private List<CourseDTO> courses; // Danh sach khoa hoc
}
