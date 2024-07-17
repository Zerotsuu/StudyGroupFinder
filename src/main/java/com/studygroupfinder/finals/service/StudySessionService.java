package com.studygroupfinder.finals.service;

import com.studygroupfinder.finals.exception.ResourceNotFoundException;
import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.StudySession;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.repository.StudyGroupRepository;
import com.studygroupfinder.finals.repository.StudySessionRepository;
import com.studygroupfinder.finals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudySessionService {
    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Transactional
    public StudySession createStudySession(StudySession studySession) {
        if (studySession.getStudyGroup() == null || studySession.getStudyGroup().getId() == null) {
            throw new IllegalArgumentException("Study group must be specified");
        }

        StudyGroup studyGroup = studyGroupRepository.findById(studySession.getStudyGroup().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid study group ID"));

        studySession.setStudyGroup(studyGroup);
        return studySessionRepository.save(studySession);
    }


    public List<StudySession> getSessionsByStudyGroup(Long studyGroupId) {
        return studySessionRepository.findByStudyGroupId(studyGroupId);
    }

    public StudySession getStudySessionById(Long id) {
        return studySessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study session not found with id: " + id));
    }

    @Transactional
    public StudySession updateStudySession(Long id, StudySession sessionDetails) {
        StudySession session = getStudySessionById(id);
        session.setDateTime(sessionDetails.getDateTime());
        session.setLocation(sessionDetails.getLocation());
        session.setDescription(sessionDetails.getDescription());
        return studySessionRepository.save(session);
    }

    @Transactional
    public void deleteStudySession(Long id) {
        StudySession session = getStudySessionById(id);
        studySessionRepository.delete(session);
    }
    public List<StudySession> getUpcomingSessionsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        LocalDateTime now = LocalDateTime.now();

        return user.getJoinedGroups().stream()
                .flatMap(group -> group.getStudySessions().stream())
                .filter(session -> session.getDateTime().isAfter(now))
                .sorted((s1, s2) -> s1.getDateTime().compareTo(s2.getDateTime()))
                .collect(Collectors.toList());
    }
}
