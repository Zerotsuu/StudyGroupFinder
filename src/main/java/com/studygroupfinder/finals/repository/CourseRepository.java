package com.studygroupfinder.finals.repository;

import com.studygroupfinder.finals.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
