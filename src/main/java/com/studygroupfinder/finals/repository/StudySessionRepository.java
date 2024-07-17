package com.studygroupfinder.finals.repository;

import com.studygroupfinder.finals.model.StudyGroup;
import com.studygroupfinder.finals.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByStudyGroupId(Long studyGroupId);
    void deleteByStudyGroupId(Long studyGroupId);
}
