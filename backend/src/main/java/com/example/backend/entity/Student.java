package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {
    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode; // Mã học sinh

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String note; // Ghi chú thêm nếu cần

    @ManyToMany(mappedBy = "students")
    private List<ClassGroup> classGroups;
}

