package com.example.backend.repository;

import com.example.backend.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassGroupId(Long classId);
}
