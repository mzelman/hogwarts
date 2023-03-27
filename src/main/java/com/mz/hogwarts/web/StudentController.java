package com.mz.hogwarts.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.pojo.PointUpdater;
import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.service.CourseService;
import com.mz.hogwarts.service.GradeService;
import com.mz.hogwarts.service.HouseService;
import com.mz.hogwarts.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class StudentController {

    @Autowired
    StudentService studentService;
    
    @Autowired
    HouseService houseService;

    @Autowired
    GradeService gradeService;

    @Autowired
    CourseService courseService;
    
    @GetMapping("/houses/{houseId}/student/{studentId}/page")
    public String studentPage(Model model, @PathVariable Long houseId, @PathVariable Long studentId) {
        addBasicModelAttributesStudent(model, studentId);
        return "student";
    }

    @PostMapping("/houses/{houseId}/student/{studentId}/page")
    public String enrollToCourse(Model model, @PathVariable Long studentId, Course course) {
        if (course.getId() == null) {
            model.addAttribute("status", "failed");
            addBasicModelAttributesStudent(model, studentId);
            return "student";
        }
        studentService.enrollToCourse(studentId, courseService.getCourseById(course.getId()));
        return "redirect:/houses/{houseId}/student/{studentId}/page";
    }

    @GetMapping("/course/{courseId}/students")
    public String courseStudentList(Model model, @PathVariable Long courseId) {
        addBasicModelAttributes(model, courseId);
        return "students";
    }

    @PostMapping("course/{courseId}/student/{studentId}/grade")
    public String addGrade(Model model, @PathVariable Long courseId, @PathVariable Long studentId, 
                            @Valid Grade grade, BindingResult result) {
        if (result.hasErrors()) {
            addBasicModelAttributes(model, courseId);
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "students";
        }
        gradeService.saveGrade(courseId, studentId, grade);
        return "redirect:/course/{courseId}/students";
    }

    @PostMapping("/course/{courseId}/enroll")
    public String enrollStudent(Model model, @PathVariable Long courseId, Student student) {
        if (student.getId() == null) {
            model.addAttribute("status", "failed2");
            addBasicModelAttributes(model, courseId);
            return "students";
        }
        courseService.enrollStudent(courseId, studentService.getStudentById(student.getId()));
        return "redirect:/course/{courseId}/students";
    }

    @GetMapping("/course/{courseId}/delete/{studentId}")
    public String deleteStudentFromCourse(Model model, @PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(courseId, studentId);
        return "redirect:/course/{courseId}/students";
    }

    @PostMapping("/course/{courseId}/house/{houseId}/add-points")
    public String addPoints(Model model, @PathVariable Long houseId, @PathVariable Long courseId, 
                            @Valid PointUpdater points, BindingResult result, 
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addBasicModelAttributes(model, courseId);
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "students";
        }
        houseService.addPoints(houseId, points.getNumber());
        redirectAttributes.addFlashAttribute("status", "success");
        redirectAttributes.addFlashAttribute("numOfPoints", points.getNumber());
        redirectAttributes.addFlashAttribute("studentHouse", houseService.getHouse(houseId).getName());
        return "redirect:/course/{courseId}/students";
    }

    @PostMapping("/course/{courseId}/house/{houseId}/substract-points")
    public String substractPoints(Model model, @PathVariable Long houseId, 
                                    @PathVariable Long courseId, @Valid PointUpdater points, 
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addBasicModelAttributes(model, courseId);
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "students";
        }
        houseService.substractPoints(houseId, points.getNumber());
        redirectAttributes.addFlashAttribute("status", "failed");
        redirectAttributes.addFlashAttribute("numOfPoints", points.getNumber());
        redirectAttributes.addFlashAttribute("studentHouse", houseService.getHouse(houseId).getName());
        return "redirect:/course/{courseId}/students";
    }

    private void addBasicModelAttributes(Model model, Long courseId) {
        Map<Long, Double> averages = gradeService.averageGrades(courseService.getAllEnrolledStudents(courseId), courseId);
        model.addAttribute("notEnrolledStudents", courseService.getNotEnrolledStudents(courseId));
        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("students", courseService.getAllEnrolledStudents(courseId));
        model.addAttribute("student", new Student());
        model.addAttribute("grade", new Grade());
        model.addAttribute("points", new PointUpdater());
        model.addAttribute("averages", averages);
    }

    private void addBasicModelAttributesStudent(Model model, Long studentId) {
        Map<Long, Double> averages = gradeService
        .averageStudentGrades(studentService.getStudentById(studentId).getCourses(), studentId);
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("averages", averages);
        model.addAttribute("notEnrolledCourses", studentService.getNotEnrolledCourses(studentId));
        model.addAttribute("course", new Course());
        model.addAttribute("courses", courseService.getAllStudentCourses(studentId));
    }
}
