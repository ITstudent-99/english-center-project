package com.example.backend.entity;

import com.example.backend.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroup extends BaseEntity{
    @Column(name = "class_code", nullable = false, unique = true)
    private String classCode; // Mã lớp học

    @Column(nullable = false, unique = true)
    private String name; // Tên lớp học

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Constants.ClassStatus status; // Trạng thái lớp học

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Constants.LearningMode learningMode; // OFFLINE, ONLINE, HYBRID

    @Column(name = "current_students", nullable = false)
    private int currentStudents = 0; // Số lượng hiện tại

    @Column(name = "max_students", nullable = false)
    private int maxStudents; // Số lượng học viên tối đa

    // @ManyToOne: giáo viên
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teacher_code", referencedColumnName = "teacher_code", nullable = false)
    private Teacher teacher;

    // @ManyToMany: học viên
    @ManyToMany
    @JoinTable(
            name = "class_students",
            joinColumns = @JoinColumn(name = "class_code", referencedColumnName = "class_code"),
            inverseJoinColumns = @JoinColumn(name = "student_code", referencedColumnName = "student_code")
    )
    private List<Student> students;

    // @ManyToMany: các khóa học
    @ManyToMany
    @JoinTable(
            name = "class_courses",
            joinColumns = @JoinColumn(name = "class_code", referencedColumnName = "class_code"),
            inverseJoinColumns = @JoinColumn(name = "course_code", referencedColumnName = "course_code")
    )
    private List<Course> courses;

    @OneToMany(mappedBy = "classGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSchedule> schedules;
}
