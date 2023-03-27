package com.mz.hogwarts.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mz.hogwarts.pojo.Course;
import com.mz.hogwarts.service.CourseService;
import com.mz.hogwarts.service.GradeService;
import com.mz.hogwarts.service.HouseService;
import com.mz.hogwarts.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    GradeService gradeService;

    @Autowired
    HouseService houseService;
    
    @GetMapping("/courses/page/{pageNum}")
    public String getCourses(Model model, @PathVariable(required = false) String pageNum) {
        setUpPagination(3, pageNum, model);
        model.addAttribute("course", new Course());
        return "courses";
    }

    @GetMapping("/course/new")
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());
        return "newcourse";
    }

    @PostMapping("/course/new/add")
    public String addCourse(Model model, @Valid Course course, BindingResult result, RedirectAttributes redirectAttributes) {
        model.addAttribute("course", course);
        if (result.hasErrors()) {
            model.addAttribute("course", course);
            return "newcourse";
        }
        if (courseService.checkCourseDuplicate(course)) {
            redirectAttributes.addFlashAttribute("status", "failed");
            return "redirect:/course/new";
        }
        redirectAttributes.addFlashAttribute("status", "success");
        courseService.addCourse(course);
        return "redirect:/courses/page/1";
    }

    @GetMapping("/course/{courseId}")
    public String coursePage(Model model, @PathVariable Long courseId) {
        model.addAttribute("course", courseService.getCourseById(courseId));
        return "course";
    }

    @GetMapping("/course/{courseId}/edit")
    public String editCourse(Model model, @PathVariable Long courseId) {
        model.addAttribute("course", courseService.getCourseById(courseId));
        return "courseform";
    }

    @PostMapping("/course/{courseId}/update")
    public String updateCourse(Model model, @PathVariable Long courseId, @Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            course.setId(courseId);
            model.addAttribute("course", course);
            return "courseform";
        }
        courseService.editCourse(courseId, course);
        return "redirect:/course/{courseId}";
    }

    @GetMapping("/course/{courseId}/delete")
    public String deleteCourse(Model model, @PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/courses/page/1";
    }

    public void setUpPagination(int pageSize, String pageNum, Model model) {
        if (pageNum == null) {
            pageNum = "1";
        }
        Page<Course> page = courseService.findPaginated(Integer.parseInt(pageNum), pageSize, "code");
        List<Course> courses = page.getContent();
        model.addAttribute("currentPage", Integer.parseInt(pageNum));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("courses", courses);
    }
}
