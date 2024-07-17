package com.studygroupfinder.finals.controller;

import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.service.CourseService;
import com.studygroupfinder.finals.service.StudySessionService;
import com.studygroupfinder.finals.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudySessionService studySessionService;
//get user by ID and show user-profile.jsp
    @GetMapping("/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("profileUser", user);
        model.addAttribute("enrolledCourses", userService.getEnrolledCourses(id));
        model.addAttribute("joinedGroups", userService.getJoinedStudyGroups(id));
        model.addAttribute("availableCourses", courseService.getAvailableCoursesForUser(id));
        model.addAttribute("upcomingStudySessions", studySessionService.getUpcomingSessionsForUser(id));
        model.addAttribute("title", user.getUsername() + "'s Profile");
        model.addAttribute("content", "user-profile.jsp");
        return "layout";
    }
//add courses to user
    @PostMapping("/add-course")
    public String addCourse(@RequestParam Long courseId, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        userService.addCourseToUser(currentUser.getId(), courseId);
        return "redirect:/users/" + currentUser.getId();
    }
}
