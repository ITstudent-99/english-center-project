package com.example.backend.repository;

import com.example.backend.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByTeacherCode(String teacherCode);

    @Query("""
    SELECT t FROM Teacher t
        WHERE (:teacherCode IS NULL OR t.teacherCode LIKE %:teacherCode%)
          AND (:name IS NULL OR t.name LIKE %:name%)
          AND (:email IS NULL OR t.email LIKE %:email%)
""")
    Page<Teacher> filterTeachers(
            Pageable pageable,
            @Param("teacherCode") String teacherCode,
            @Param("name") String name,
            @Param("email") String email
    );
}

