package com.mz.hogwarts.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mz.hogwarts.pojo.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByStudentsId(Long studentId);
    List<Course> findByStudentsId(Long studentId, Sort sort);
}
