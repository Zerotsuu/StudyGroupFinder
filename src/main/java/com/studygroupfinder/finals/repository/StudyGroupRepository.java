package com.studygroupfinder.finals.repository;

import com.studygroupfinder.finals.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    List<StudyGroup> findByCourseId(Long courseId);

//use custom queries for search feature
    @Query("SELECT sg FROM StudyGroup sg WHERE LOWER(sg.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(sg.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<StudyGroup> searchStudyGroups(@Param("searchTerm") String searchTerm);
}
