package com.mz.hogwarts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import com.mz.hogwarts.pojo.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long>{
    List<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId, Sort sort);
}
