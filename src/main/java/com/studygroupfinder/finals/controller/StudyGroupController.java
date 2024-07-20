package com.studygroupfinder.finals.controller;


import com.studygroupfinder.finals.form.RegistrationForm;
import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.StudySession;
import com.studygroupfinder.finals.model.Task;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/study-groups")
public class StudyGroupController {
    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private StudySessionService studySessionService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;
//Display list-study-groups.jsp with all groups
    @GetMapping
    public String listStudyGroups(Model model) {
        List<StudyGroup> studyGroups = studyGroupService.getAllStudyGroups();
        model.addAttribute("studyGroups", studyGroups);
        model.addAttribute("title", "Study Groups");
        model.addAttribute("content", "list-study-groups.jsp");
        return "layout";
    }
//Display Modal
    @GetMapping("/create-modal")
    public String showCreateForm(Model model) {
        model.addAttribute("studyGroup", new StudyGroup());
        model.addAttribute("courses", courseService.getAllCourses());
        return "create-study-group-modal";
    }
//Push studygroup data into jpa/sql
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createStudyGroup(@ModelAttribute StudyGroup studyGroup, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Map.of("success", false, "message", "User not logged in");
        }

        try {
            StudyGroup createdGroup = studyGroupService.createStudyGroup(studyGroup, currentUser);
            return Map.of("success", true, "groupId", createdGroup.getId());
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
//Display studygroup details with tasks attached
    @GetMapping("/{id}")
    public String viewStudyGroup(@PathVariable Long id, @RequestParam(defaultValue = "") String sortBy, Model model) {
        StudyGroup studyGroup = studyGroupService.getStudyGroupById(id);
        List<StudySession> studySessions = studySessionService.getSessionsByStudyGroup(id);
        List<Task> tasks = taskService.getTasksByStudyGroupSorted(id, sortBy);

        model.addAttribute("studyGroup", studyGroup);
        model.addAttribute("studySessions", studySessions);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("title", studyGroup.getName());
        model.addAttribute("content", "view-study-group.jsp");
        return "layout";
    }
//display edit-study-group modal
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudyGroup studyGroup = studyGroupService.getStudyGroupById(id);
        model.addAttribute("studyGroup", studyGroup);
        model.addAttribute("courses", courseService.getAllCourses());
        return "edit-study-group";
    }
//handles editing of studygroup
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateStudyGroup(@ModelAttribute StudyGroup studyGroup) {
        try {
            StudyGroup updatedGroup = studyGroupService.updateStudyGroup(studyGroup.getId(), studyGroup);
            return Map.of("success", true, "groupId", updatedGroup.getId());
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
//immediately delete studygroup by group id
    @PostMapping("/{id}/delete")
    public String deleteStudyGroup(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        StudyGroup studyGroup = studyGroupService.getStudyGroupById(id);

        // Check if the current user is the creator (if exists) or an admin
        if ((studyGroup.getCreator() != null && currentUser.getId().equals(studyGroup.getCreator().getId())) ||
                currentUser.getRole() == User.Role.ADMIN) {
            studyGroupService.deleteStudyGroup(id);
            return "redirect:/study-groups";
        } else {
            // Handle unauthorized deletion attempt
            return "redirect:/study-groups/" + id + "?error=unauthorized";
        }
    }
//make user join group by joining group id and user id
    @PostMapping("/{id}/join")
    public String joinStudyGroup(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            System.out.println("User not found in session");
            return "redirect:/login";
        }

        System.out.println("User " + currentUser.getId() + " attempting to join group " + id);
        studyGroupService.joinStudyGroup(id, currentUser.getId());

        // Refresh the user in the session
        User updatedUser = userService.getUserById(currentUser.getId());
        session.setAttribute("user", updatedUser);
        System.out.println("Updated user in session. Joined groups: " + updatedUser.getJoinedGroups());

        return "redirect:/study-groups/" + id;
    }
//remove user id in joined table
    @PostMapping("/{id}/leave")
    public String leaveStudyGroup(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        studyGroupService.leaveStudyGroup(id, currentUser.getId());
        return "redirect:/study-groups/" + id;
    }
//display tasks page
    @GetMapping("/{id}/tasks")
    public String viewTasks(@PathVariable Long id, @RequestParam(defaultValue = "") String sortBy, Model model) {
        StudyGroup studyGroup = studyGroupService.getStudyGroupById(id);
        List<Task> tasks = taskService.getTasksByStudyGroupSorted(id, sortBy);
        Task task = taskService.getTaskById(id);

        model.addAttribute("studyGroup", studyGroup);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        model.addAttribute("task", new Task());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("content", "view-tasks.jsp");
        return "layout";
    }
//create tasks from modal
    @PostMapping("/{id}/tasks/create")
    public String createTask(@PathVariable Long id, @ModelAttribute Task task, Model model) {
        StudyGroup studyGroup = studyGroupService.getStudyGroupById(id);
        model.addAttribute("newTask", new Task());
        task.setStudyGroup(studyGroup);
        taskService.createTask(task);
        return "redirect:/study-groups/" + id + "/tasks";
    }
//edit tasks from modal based on clicked id
    @PostMapping("/{id}/tasks/{taskId}/edit")
    public String editTask(@PathVariable Long id, @PathVariable Long taskId, @ModelAttribute Task task) {
        Task existingTask = taskService.getTaskById(taskId);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setCompleted(task.isCompleted());
        taskService.updateTask(existingTask);
        return "redirect:/study-groups/" + id + "/tasks";
    }
//delete task based on clicked id
    @PostMapping("/{id}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable Long id, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/study-groups/" + id + "/tasks";
    }
//display create session modal
    @GetMapping("/{id}/sessions/create")
    public String showCreateSessionModal(@PathVariable Long id, Model model) {
        model.addAttribute("studySession", new StudySession());
        model.addAttribute("studyGroupId", id);
        return "create-study-session-modal";
    }
}