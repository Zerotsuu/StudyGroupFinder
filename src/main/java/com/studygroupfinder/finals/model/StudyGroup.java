package com.studygroupfinder.finals.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


@Entity
public class StudyGroup {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User creator;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "joinedGroups")
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudySession> studySessions = new HashSet<>();

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StudySession> getStudySessions() {
        return studySessions;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setStudyGroup(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setStudyGroup(null);
    }
}
