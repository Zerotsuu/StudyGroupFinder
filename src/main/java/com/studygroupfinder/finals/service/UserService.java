package com.studygroupfinder.finals.service;

import com.studygroupfinder.finals.exception.ResourceNotFoundException;
import com.studygroupfinder.finals.exception.UserAlreadyExistsException;
import com.studygroupfinder.finals.model.Course;
import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.CourseRepository;
import com.studygroupfinder.finals.repository.StudyGroupRepository;
import com.studygroupfinder.finals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {

            return user;
        }
        return null;
    }

    @Transactional
    public User createUser(User user) {
        if (user.getEnrolledCourses() == null) {
            user.setEnrolledCourses(new ArrayList<>());
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (user.getEnrolledCourses() == null) {
            user.setEnrolledCourses(new ArrayList<>());
        }
        return user;
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setAcademicInterests(userDetails.getAcademicInterests());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public List<Course> getEnrolledCourses(Long userId) {
        User user = getUserById(userId);
        return user.getEnrolledCourses();
    }

    @Transactional
    public void addCourseToUser(Long userId, Long courseId) {
        User user = getUserById(userId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (!user.getEnrolledCourses().contains(course)) {
            user.getEnrolledCourses().add(course);
            userRepository.save(user);
        }
    }

    public Set<StudyGroup> getJoinedStudyGroups(Long userId) {
        User user = getUserById(userId);
        return user.getJoinedGroups();
    }
}