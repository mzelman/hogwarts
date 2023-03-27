package com.mz.hogwarts.service;

import java.util.List;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Student;

public interface StudentService {
    public List<Student> getAllStudents();
    public Student getStudentById(Long id);
    public Student getStudentByFirstNameAndLastName(String firstName, String lastName);
    public void addStudent(Student student, Long houseId);
    public void deleteStudent(Long studentId, Long houseId);
    public void enrollToCourse(Long studentId, Course course);
    public List<Course> getNotEnrolledCourses(Long studentId);
}
