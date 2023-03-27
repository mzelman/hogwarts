package com.mz.hogwarts.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Student;

public interface CourseService {
    public List<Course> getCourses();
    public List<Course> getAllStudentCourses(Long studentId);
    public Course getCourseById(Long id);
    public void addCourse(Course course);
    public void deleteCourse(Long id);
    public void editCourse(Long id, Course course);
    public void enrollStudent(Long id, Student student);
    public void deleteStudentFromCourse(Long courseId, Long studentId);
    public List<Student> getAllEnrolledStudents(Long id);
    public List<Student> getNotEnrolledStudents(Long courseId);
    public Page<Course> findPaginated(int pageNum, int pageSize, String sortField);
    public boolean checkCourseDuplicate(Course course);
}
