package com.mz.hogwarts.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mz.hogwarts.pojo.Student;
import com.mz.hogwarts.service.HouseService;
import com.mz.hogwarts.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HousesController {
    
    @Autowired
    HouseService houseService;

    @Autowired
    StudentService studentService;

    @GetMapping("/houses")
    public String getHouses(Model model) {
        model.addAttribute("houses", houseService.getHouses());
        return "houses";
    }

    @GetMapping("/houses/{id}")
    public String studentList(Model model, @PathVariable Long id, Student student) {
        model.addAttribute("students", houseService.getStudents(id));
        model.addAttribute("house", houseService.getHouse(id));
        return "house";
    }

    @PostMapping("/houses/{houseId}/student")
    public String addStudent(Model model, @Valid Student student, BindingResult result, 
                            @PathVariable Long houseId, 
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("students", houseService.getStudents(houseId));
            model.addAttribute("house", houseService.getHouse(houseId));
            return "house";
        }
        Student checkStudent = studentService.getStudentByFirstNameAndLastName(student.getFirstName(), student.getLastName());
        if (checkStudent != null) {
            redirectAttributes.addFlashAttribute("status", "failed");
            redirectAttributes.addFlashAttribute("studentHouse", checkStudent.getHouse().getName());
            return "redirect:/houses/{houseId}";
        }
        studentService.addStudent(student, houseId);
        redirectAttributes.addFlashAttribute("status", "success");
        return "redirect:/houses/{houseId}";
    }

    @GetMapping("/houses/{houseId}/student/{studentId}")
    public String deleteStudent(Model model, @PathVariable Long houseId, @PathVariable Long studentId, Student student) {
        studentService.deleteStudent(studentId, houseId);
        return "redirect:/houses/{houseId}";
    }
}
