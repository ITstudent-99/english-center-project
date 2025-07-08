package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends BaseEntity {
    @Column(name = "teacher_code", nullable = false, unique = true)
    private String teacherCode; // Mã giáo viên

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String specialization; // chuyên môn: IELTS, Giao tiếp, Starter...

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<ClassGroup> classGroups;
}

