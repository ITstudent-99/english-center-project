package com.example.backend.entity;

import com.example.backend.utils.Constants;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Constants.Role role;

    @OneToOne
    @JoinColumn(name = "teacher_code", referencedColumnName = "teacher_code")
    private Teacher teacher; // Có thể null

    @OneToOne
    @JoinColumn(name = "student_code", referencedColumnName = "student_code")
    private Student student; // có thể null
}
