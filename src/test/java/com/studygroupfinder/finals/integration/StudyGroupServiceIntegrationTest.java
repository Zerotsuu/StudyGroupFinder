package com.studygroupfinder.finals.integration;

import com.studygroupfinder.finals.model.Course;
import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.CourseRepository;
import com.studygroupfinder.finals.repository.StudyGroupRepository;
import com.studygroupfinder.finals.repository.UserRepository;
import com.studygroupfinder.finals.service.StudyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudyGroupServiceIntegrationTest {

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    private User testUser;
    private Course testCourse;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        userRepository.save(testUser);

        // Create a test course
        testCourse = new Course();
        testCourse.setName("Test Course");
        testCourse.setCode("TST101");
        courseRepository.save(testCourse);
    }

    @Test
    void testCreateStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Study Group");
        studyGroup.setCourse(testCourse);
        studyGroup.setDescription("This is a test study group");

        StudyGroup createdGroup = studyGroupService.createStudyGroup(studyGroup, testUser);

        assertNotNull(createdGroup.getId());
        assertEquals("Test Study Group", createdGroup.getName());
        assertEquals(testCourse.getId(), createdGroup.getCourse().getId());
        assertEquals(testUser.getId(), createdGroup.getCreator().getId());
    }

    @Test
    void testGetStudyGroupsByCourse() {
        // Create a study group
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Study Group");
        studyGroup.setCourse(testCourse);
        studyGroup.setDescription("This is a test study group");
        studyGroupService.createStudyGroup(studyGroup, testUser);

        List<StudyGroup> groups = studyGroupService.getStudyGroupsByCourse(testCourse.getId());

        assertFalse(groups.isEmpty());
        assertEquals(1, groups.size());
        assertEquals("Test Study Group", groups.get(0).getName());
    }

    @Test
    void testJoinStudyGroup() {
        // Create a study group
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Study Group");
        studyGroup.setCourse(testCourse);
        studyGroup.setDescription("This is a test study group");
        StudyGroup createdGroup = studyGroupService.createStudyGroup(studyGroup, testUser);

        // Create another user to join the group
        User joiningUser = new User();
        joiningUser.setUsername("joininguser");
        joiningUser.setEmail("joininguser@example.com");
        joiningUser.setPassword("password");
        userRepository.save(joiningUser);

        studyGroupService.joinStudyGroup(createdGroup.getId(), joiningUser.getId());
        entityManager.clear();
        StudyGroup updatedGroup = studyGroupRepository.findById(createdGroup.getId()).orElseThrow();
        assertTrue(updatedGroup.getMembers().contains(joiningUser));
    }

    @Test
    void testLeaveStudyGroup() {
        // Create a study group
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Study Group");
        studyGroup.setCourse(testCourse);
        studyGroup.setDescription("This is a test study group");
        StudyGroup createdGroup = studyGroupService.createStudyGroup(studyGroup, testUser);

        // Join the group
        studyGroupService.joinStudyGroup(createdGroup.getId(), testUser.getId());

        // Leave the group
        studyGroupService.leaveStudyGroup(createdGroup.getId(), testUser.getId());

        studyGroupRepository.flush();
        StudyGroup updatedGroup = studyGroupRepository.findById(createdGroup.getId()).orElseThrow();
        assertFalse(updatedGroup.getMembers().contains(testUser));
    }
}