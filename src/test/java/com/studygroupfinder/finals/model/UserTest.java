package com.studygroupfinder.finals.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.STUDENT);

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(User.Role.STUDENT, user.getRole());
    }

    @Test
    public void testUserRoleAssignment() {
        User user = new User();
        user.setRole(User.Role.ADMIN);

        assertEquals(User.Role.ADMIN, user.getRole());
    }

    @Test
    public void testUserEnrolledCoursesInitialization() {
        User user = new User();
        assertNotNull(user.getEnrolledCourses(), "Enrolled courses should be initialized");
        assertTrue(user.getEnrolledCourses().isEmpty(), "Enrolled courses should be empty initially");
    }
}