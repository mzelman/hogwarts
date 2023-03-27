package com.mz.hogwarts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.CourseRepository;
import com.mz.hogwarts.repository.GradeRepository;
import com.mz.hogwarts.repository.StudentRepository;

@Service
public class GradeServiceImpl implements GradeService {
    
    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    public List<Grade> getStudentCourseGrades(Long studentId, Long courseId) {
        return gradeRepository.findByStudentIdAndCourseId(studentId, courseId, Sort.by("id"));
    }

    public Grade getGradeById(Long gradeId) {
        return unwrap(gradeRepository.findById(gradeId), gradeId);
    }

    public void saveGrade(Long courseId, Long studentId, Grade grade) {
        grade.setCourse(CourseServiceImpl.unwrap(courseRepository.findById(courseId), courseId));
        grade.setStudent(StudentServiceImpl.unwrap(studentRepository.findById(studentId), studentId));
        gradeRepository.save(grade);
    }

    public void deleteGrade(Grade grade) {
        gradeRepository.delete(grade);
    }

    public Map<Long, Double> averageGrades(List<Student> students, Long courseId) {
        Map<Long, Double> avgGrades = new HashMap<Long, Double>();
        for (Student student : students) {
            double avgGrade = 0;
            int sum = 0;
            int numOfGrades = 0;
            for (Grade grade : getStudentCourseGrades(student.getId(), courseId)) {
                sum += Integer.parseInt(grade.getScore());
                numOfGrades++;
            }
            avgGrade = Math.round((double)sum / numOfGrades * 2) / 2.0;
            avgGrades.put(student.getId(), avgGrade);
        }
        return avgGrades;
    }

    public Map<Long, Double> averageStudentGrades(List<Course> courses, Long studentId) {
        Map<Long, Double> avgGrades = new HashMap<Long, Double>();
        for (Course course : courses) {
            double avgGrade = 0;
            int sum = 0;
            int numOfGrades = 0;
            for (Grade grade : course.getGrades()) {
                if (grade.getStudent().getId() == studentId) {
                    sum += Integer.parseInt(grade.getScore());
                    numOfGrades++;
                }
            }
            avgGrade = Math.round((double)sum / numOfGrades * 2) / 2.0;
            avgGrades.put(course.getId(), avgGrade);
        }
        return avgGrades;
    }

    static Grade unwrap(Optional<Grade> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new IndexOutOfBoundsException(id);
    }
}
