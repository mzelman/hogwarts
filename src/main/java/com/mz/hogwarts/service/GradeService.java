package com.mz.hogwarts.service;

import java.util.List;
import java.util.Map;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.pojo.Student;

public interface GradeService {
    public List<Grade> getStudentCourseGrades(Long studentId, Long courseId);
    public Grade getGradeById(Long gradeId);
    public void saveGrade(Long courseId, Long studentId, Grade grade);
    public void deleteGrade(Grade grade);
    public Map<Long, Double> averageGrades(List<Student> students, Long courseId);
    public Map<Long, Double> averageStudentGrades(List<Course> courses, Long studentId);
}
