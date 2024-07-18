package com.studygroupfinder.finals.controller;

import com.studygroupfinder.finals.model.Course;
import com.studygroupfinder.finals.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//test testing ci/cd
@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

//Go to page admin/courses
    @GetMapping
    public String adminCoursesPage(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("title", "Course Management");
        model.addAttribute("content", "admin-courses.jsp");
        return "layout";
    }
//creation of courses
    @PostMapping("/create")
    public String createCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "redirect:/admin/courses";
    }
//update course
    @PostMapping("/edit")
    public String editCourse(@ModelAttribute Course course) {
        courseService.updateCourse(course);
        return "redirect:/admin/courses";
    }
//delete courses by ID
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}