package com.studygroupfinder.finals.service;

import com.studygroupfinder.finals.model.Task;
import com.studygroupfinder.finals.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByStudyGroupSorted(Long studyGroupId, String sortBy) {
        switch (sortBy) {
            case "dueDate_asc":
                return taskRepository.findByStudyGroupIdOrderByDueDateAsc(studyGroupId);
            case "dueDate_desc":
                return taskRepository.findByStudyGroupIdOrderByDueDateDesc(studyGroupId);
            case "priority_desc":
                return taskRepository.findByStudyGroupIdOrderByPriorityDesc(studyGroupId);
            case "priority_asc":
                return taskRepository.findByStudyGroupIdOrderByPriorityAscDueDateAsc(studyGroupId);
            default:
                return taskRepository.findByStudyGroupId(studyGroupId);
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}