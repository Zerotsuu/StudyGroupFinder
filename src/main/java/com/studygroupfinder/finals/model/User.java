package com.studygroupfinder.finals.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @Column(columnDefinition = "TEXT")
    private String academicInterests;

    @ManyToMany(mappedBy = "members")
    private Set<StudyGroup> studyGroups;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> enrolledCourses = new ArrayList<>();
//Getters and Setters
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<StudyGroup> getJoinedGroups() {
        return joinedGroups;
    }

    public void setJoinedGroups(Set<StudyGroup> joinedGroups) {
        this.joinedGroups = joinedGroups;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses == null ? new ArrayList<>() : enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public enum Role {
        STUDENT,  ADMIN
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_study_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id")
    )
    private Set<StudyGroup> joinedGroups = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcademicInterests() {
        return academicInterests;
    }

    public void setAcademicInterests(String academicInterests) {
        this.academicInterests = academicInterests;
    }
}
