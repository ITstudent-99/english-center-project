package com.example.backend.repository;

import com.example.backend.entity.ClassGroup;
import com.example.backend.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRepository extends JpaRepository <ClassGroup, Long>{
    @Query("""
    SELECT c FROM ClassGroup c
    LEFT JOIN c.teacher t
    LEFT JOIN c.courses cs
    WHERE (:classCode IS NULL OR c.classCode LIKE %:classCode%)
    AND (:name IS NULL OR c.name LIKE %:name%)
    AND (:teacherName IS NULL OR t.name LIKE %:teacherName%)
    AND (:status IS NULL OR c.status = :status)
    AND (:learningMode IS NULL OR c.learningMode = :learningMode)
    AND (:courseName IS NULL OR cs.name LIKE %:courseName%)
""")
    Page<ClassGroup> filterClasses(
            @Param("classCode") String classCode,
            @Param("name") String name,
            @Param("teacherName") String teacherName,
            @Param("status") Constants.ClassStatus status,
            @Param("learningMode") Constants.LearningMode learningMode,
            @Param("courseName") String courseName,
            Pageable pageable
    );

    ClassGroup findByClassCode(String classCode);
}
