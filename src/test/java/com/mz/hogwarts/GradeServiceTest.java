package com.mz.hogwarts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.pojo.House;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.repository.CourseRepository;
import com.mz.hogwarts.repository.GradeRepository;
import com.mz.hogwarts.repository.StudentRepository;
import com.mz.hogwarts.service.GradeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GradeRepository gradeRepository;
    
    @InjectMocks
    private GradeServiceImpl gradeService;

    Course course = new Course(1L, "POT", "Potions", "Potions are made.", null, null);
    House house = new House(1L, "Gryffindor", 0, null);
    Student student = new Student(1L, "Harry", "Potter", house, null, null);
    Grade grade1 = new Grade(1L, "6", student, course);
    Grade grade2 = new Grade(1L, "3", student, course);
    List<Grade> grades = Arrays.asList(grade1, grade2);
    List<Student> students = Arrays.asList(student);
    List<Course> courses = Arrays.asList(course);

    @Test
    public void averageGradesTest() {
        when(gradeRepository.findByStudentIdAndCourseId(1L, 1L, Sort.by("id")))
        .thenReturn(Arrays.asList(grade1, grade2));
        assertEquals(4.5, gradeService.averageGrades(students, 1L).get(1L));

    }

    @Test
    public void averageStudentGradesTest() {
        course.setGrades(grades);
        assertEquals(4.5, gradeService.averageStudentGrades(courses, 1L).get(1L));
    }
}
