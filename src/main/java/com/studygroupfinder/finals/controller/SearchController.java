package com.studygroupfinder.finals.controller;

import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private StudyGroupService studyGroupService;

//display page of searched term in input field
    @GetMapping
    public String search(@RequestParam String query, Model model) {
        List<StudyGroup> searchResults = studyGroupService.searchStudyGroups(query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("query", query);
        model.addAttribute("title", "Search Results");
        model.addAttribute("content", "search-results.jsp");
        return "layout";
    }
}
