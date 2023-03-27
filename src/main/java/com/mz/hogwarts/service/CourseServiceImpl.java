package com.mz.hogwarts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.CourseRepository;
import com.mz.hogwarts.repository.GradeRepository;
import com.mz.hogwarts.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GradeRepository gradeRepository;

    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll(Sort.by("code"));
    }

    public List<Course> getAllStudentCourses(Long studentId) {
        return courseRepository.findByStudentsId(studentId, Sort.by("subject"));
    }

    public Course getCourseById(Long id) {
        return unwrap(courseRepository.findById(id), id);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.delete(getCourseById(id));
    }

    public void editCourse(Long id, Course course) {
        Course newCourse = getCourseById(id);
        newCourse.setCode(course.getCode());
        newCourse.setSubject(course.getSubject());
        newCourse.setDescription(course.getDescription());
        courseRepository.save(newCourse);
    }

    public void enrollStudent(Long courseId, Student student) {
        unwrap(courseRepository.findById(courseId), courseId).getStudents().add(student);
        courseRepository.save(unwrap(courseRepository.findById(courseId), courseId));
    }

    public void deleteStudentFromCourse(Long courseId, Long studentId) {
        List<Grade> grades = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        for (Grade grade : grades) {
            gradeRepository.delete(grade);
        }
        unwrap(courseRepository.findById(courseId), courseId)
            .getStudents()
            .remove(StudentServiceImpl.unwrap(studentRepository
            .findById(studentId), studentId));
        courseRepository.save(unwrap(courseRepository.findById(courseId), courseId)); 
    }

    public List<Student> getAllEnrolledStudents(Long courseId) {
        return studentRepository.findByCoursesId(courseId, Sort.by("lastName").ascending().and(Sort.by("firstName")));
    }

    public List<Student> getNotEnrolledStudents(Long courseId) {
        List<Student> students = (studentRepository.findAll(Sort.by("lastName").and(Sort.by("firstName"))));
        List<Student> courseStudents = new ArrayList<>(studentRepository.findByCoursesId(courseId));
        students.removeAll(courseStudents);
        return students;
    }

    public Page<Course> findPaginated(int pageNum, int pageSize, String sortField) {
        Sort sort = Sort.by(sortField);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return courseRepository.findAll(pageable);
    }

    public boolean checkCourseDuplicate(Course course) {
        for (int i = 0; i < courseRepository.findAll().size(); i++) {
            if (courseRepository.findAll().get(i).getCode().equals(course.getCode()) ||
            courseRepository.findAll().get(i).getSubject().equals(course.getSubject())) {
                return true;
            }
        }
        return false;
    }

    static Course unwrap(Optional<Course> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
    }
}
