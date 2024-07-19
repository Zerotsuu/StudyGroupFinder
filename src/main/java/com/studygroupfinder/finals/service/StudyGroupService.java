package com.studygroupfinder.finals.service;


import com.studygroupfinder.finals.exception.ResourceNotFoundException;
import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.repository.StudySessionRepository;
import com.studygroupfinder.finals.repository.UserRepository;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudyGroupService {
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public StudyGroup createStudyGroup(StudyGroup studyGroup, User creator) {
        studyGroup.setCreator(creator);
        return studyGroupRepository.save(studyGroup);
    }

    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }

    public List<StudyGroup> getStudyGroupsByCourse(Long courseId) {
        return studyGroupRepository.findByCourseId(courseId);
    }

    public StudyGroup getStudyGroupById(Long id) {
        return studyGroupRepository.findById(id).orElse(null);
    }

    public StudyGroup updateStudyGroup(Long id, StudyGroup studyGroupDetails) {
        StudyGroup studyGroup = getStudyGroupById(id);
        studyGroup.setName(studyGroupDetails.getName());
        studyGroup.setDescription(studyGroupDetails.getDescription());
        studyGroup.setCourse(studyGroupDetails.getCourse());
        return studyGroupRepository.save(studyGroup);
    }

    public List<StudyGroup> searchStudyGroups(String searchTerm) {
        return studyGroupRepository.searchStudyGroups(searchTerm);
    }

    @Transactional
    public void deleteStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study group not found with id: " + id));

        // Remove the study group from all members' joinedGroups
        if (studyGroup.getMembers() != null) {
            for (User member : studyGroup.getMembers()) {
                member.getJoinedGroups().remove(studyGroup);
            }
        }

        // Delete all associated study sessions
        studySessionRepository.deleteByStudyGroupId(id);

        // delete the study group
        studyGroupRepository.delete(studyGroup);
    }

    @Transactional
    public void joinStudyGroup(Long groupId, Long userId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Study group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        group.getMembers().add(user);
        studyGroupRepository.save(group);
        user.getJoinedGroups().add(group);
        userRepository.save(user);
    }

    @Transactional
    public void leaveStudyGroup(Long groupId, Long userId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Study group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        group.getMembers().remove(user);
        studyGroupRepository.save(group);
        user.getJoinedGroups().remove(group);
        userRepository.save(user);
    }
}
