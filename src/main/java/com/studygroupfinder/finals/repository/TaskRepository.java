package com.studygroupfinder.finals.repository;

import com.studygroupfinder.finals.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    //Get sorting options for task repository
    //couldn't get to make the front end to work with war file so resorted to back end sorting
    List<Task> findByStudyGroupId(Long studyGroupId);
    List<Task> findByStudyGroupIdOrderByDueDateAsc(Long studyGroupId);
    List<Task> findByStudyGroupIdOrderByDueDateDesc(Long studyGroupId);
    List<Task> findByStudyGroupIdOrderByPriorityDesc(Long studyGroupId);
    List<Task> findByStudyGroupIdOrderByPriorityAscDueDateAsc(Long studyGroupId);
}