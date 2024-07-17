package com.studygroupfinder.finals.controller;


import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.StudySession;
import com.studygroupfinder.finals.service.StudyGroupService;
import com.studygroupfinder.finals.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/study-sessions")
public class StudySessionController {
    @Autowired
    private StudySessionService studySessionService;
    @Autowired
    private StudyGroupService studyGroupService;

//push data from create study session modal
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createStudySession(@ModelAttribute StudySession studySession) {
        try {
            StudySession createdSession = studySessionService.createStudySession(studySession);
            return ResponseEntity.ok(Map.of("success", true, "sessionId", createdSession.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
//open edit session modal
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudySession studySession = studySessionService.getStudySessionById(id);
        model.addAttribute("studySession", studySession);
        return "edit-study-session";
    }

//update study session from modal
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateStudySession(@ModelAttribute StudySession studySession) {
        try {
            StudySession updatedSession = studySessionService.updateStudySession(studySession.getId(), studySession);
            return Map.of("success", true, "sessionId", updatedSession.getId());
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

//delete study sessions
    @PostMapping("/{id}/delete")
    public String deleteStudySession(@PathVariable Long id) {
        StudySession studySession = studySessionService.getStudySessionById(id);
        Long studyGroupId = studySession.getStudyGroup().getId();
        studySessionService.deleteStudySession(id);
        return "redirect:/study-groups/" + studyGroupId;
    }
}