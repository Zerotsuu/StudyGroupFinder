package com.studygroupfinder.finals.service;

import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.StudyGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyGroupServiceTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @InjectMocks
    private StudyGroupService studyGroupService;

    private StudyGroup testGroup;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testGroup = new StudyGroup();
        testGroup.setId(1L);
        testGroup.setName("Test Group");
        testGroup.setCreator(testUser);
    }

    @Test
    void testCreateStudyGroup() {
        when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(testGroup);

        StudyGroup result = studyGroupService.createStudyGroup(testGroup, testUser);

        assertNotNull(result);
        assertEquals(testGroup.getName(), result.getName());
        assertEquals(testUser, result.getCreator());
        verify(studyGroupRepository).save(any(StudyGroup.class));
    }
}