package com.mz.hogwarts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.CourseRepository;
import com.mz.hogwarts.repository.HouseRepository;
import com.mz.hogwarts.repository.StudentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Student> getAllStudents() {
        return (List<Student>)studentRepository.findAll(Sort.by("lastName").and(Sort.by("firstName")));
    }

    public Student getStudentById(Long studentId) {
        return unwrap(studentRepository.findById(studentId), studentId);
    }

    public Student getStudentByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public void addStudent(Student student, Long houseId) {
        student.setHouse(unwrapHouse(houseRepository.findById(houseId), houseId));
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId, Long houseId) {
        HouseServiceImpl.unwrapHouse(houseRepository.findById(houseId), houseId)
            .getStudents()
            .remove(getStudentById(studentId));
        for (Course course : courseRepository.findAll()) {
            course.getStudents().remove(unwrap(studentRepository.findById(studentId), studentId));
        }
        studentRepository.delete(getStudentById(studentId));
    }

    public void enrollToCourse(Long studentId, Course course) {
        Student student = unwrap(studentRepository.findById(studentId), studentId);
        unwrapCourse(courseRepository.findById(course.getId()), course.getId()).getStudents().add(student);
        courseRepository.save(unwrapCourse(courseRepository.findById(course.getId()), course.getId()));
    }

    public List<Course> getNotEnrolledCourses(Long studentId) {
        List<Course> courses = (courseRepository.findAll(Sort.by("subject")));
        List<Course> studentCourses = new ArrayList<>(courseRepository.findByStudentsId(studentId));
        courses.removeAll(studentCourses);
        return courses;
    }

    static Student unwrap(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
    }

    static Course unwrapCourse(Optional<Course> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
    }

    static House unwrapHouse(Optional<House> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
}
}
