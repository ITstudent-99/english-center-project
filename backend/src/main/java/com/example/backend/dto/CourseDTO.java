package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String courseCode;      // Mã khóa học
    private String name;            // Tên khóa học
    private String level;           // Trình độ (Beginner, Intermediate, etc.)
    private String description;     // Mô tả khóa học
    private BigDecimal duration;    // Thời lượng (theo tháng)
    private BigDecimal tuitionFee;  // Học phí
}

