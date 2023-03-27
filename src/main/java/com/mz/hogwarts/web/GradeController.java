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

import com.mz.hogwarts.pojo.Grade;
import com.mz.hogwarts.service.CourseService;
import com.mz.hogwarts.service.GradeService;
import com.mz.hogwarts.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @GetMapping("/course/{courseId}/student/{studentId}/grades")
    public String editGrades(Model model, @PathVariable Long courseId, @PathVariable Long studentId) {
        addBasicModelAttributes(model, courseId, studentId);
        model.addAttribute("grade", new Grade());
        return "grades";
    }

    @PostMapping("/course/{courseId}/student/{studentId}/grade/add")
    public String addGrade(Model model, @PathVariable Long courseId, @PathVariable Long studentId, 
                            @Valid Grade grade, BindingResult result) {
        if (result.hasErrors()) {
        addBasicModelAttributes(model, courseId, studentId);
        model.addAttribute("grade", grade);
        return "grades";
        }
        gradeService.saveGrade(courseId, studentId, grade);
        return "redirect:/course/{courseId}/student/{studentId}/grades";
    }

    @PostMapping("/course/{courseId}/student/{studentId}/grade/delete")
    public String deleteGrade(Model model, @PathVariable Long courseId, @PathVariable Long studentId, Grade grade) {
        gradeService.deleteGrade(grade);
        return "redirect:/course/{courseId}/student/{studentId}/grades";
    }

    private void addBasicModelAttributes(Model model, Long courseId, Long studentId) {
        Map<Long, Double> averages = gradeService.averageGrades(courseService.getAllEnrolledStudents(courseId), courseId);
        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("grades", gradeService.getStudentCourseGrades(studentId, courseId));
        model.addAttribute("averages", averages);
    }
}
