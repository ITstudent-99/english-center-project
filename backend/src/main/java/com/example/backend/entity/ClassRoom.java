package com.example.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom extends BaseEntity{
    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<ClassSchedule> schedules;

    @Column(nullable = false, unique = true)
    private String name; // Ví dụ: A101, B202
    private int capacity; // Sức chứa tối đa (nếu muốn kiểm tra trùng/phòng đủ chỗ)
}
