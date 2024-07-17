package com.studygroupfinder.finals.service;

import com.studygroupfinder.finals.exception.ResourceNotFoundException;
import com.studygroupfinder.finals.model.Course;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.CourseRepository;
import com.studygroupfinder.finals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Course course) {
        if (course.getId() == null) {
            throw new IllegalArgumentException("Course ID must not be null for update operation");
        }

        Course existingCourse = getCourseById(course.getId());
        existingCourse.setName(course.getName());
        existingCourse.setCode(course.getCode());
        existingCourse.setDescription(course.getDescription());
        return courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    public List<Course> getAvailableCoursesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Course> allCourses = getAllCourses();
        List<Course> enrolledCourses = user.getEnrolledCourses();

        if (enrolledCourses == null) {
            return allCourses;
        }

        return allCourses.stream()
                .filter(course -> !enrolledCourses.contains(course))
                .collect(Collectors.toList());
    }
}