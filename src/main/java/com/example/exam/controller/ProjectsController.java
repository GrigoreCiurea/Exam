package com.example.exam.controller;

import com.example.exam.exception.ProjectNotFoundException;
import com.example.exam.exception.TaskNotFoundException;
import com.example.exam.exception.UserAlreadyExistsException;
import com.example.exam.exception.UserNotFoundException;
import com.example.exam.model.Project;
import com.example.exam.model.Task;
import com.example.exam.model.User;
import com.example.exam.repository.ProjectRepository;
import com.example.exam.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectsController {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    @Transactional
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project projectToBeSaved = Project.builder()
                .name(project.getName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .description(project.getDescription())
                .build();
        return ResponseEntity.ok(projectRepository.save(projectToBeSaved));
    }
    @Transactional
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }
    @Transactional
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable(name = "projectId") UUID projectId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        return ResponseEntity.ok(project);
    }
    @Transactional
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable(name = "projectId") UUID projectId, @RequestBody Project project) {
        Project existingProject = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        existingProject.setProjectId(projectId);
        if (!project.equals(existingProject)) {
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setEndDate(project.getEndDate());
            existingProject.setStartDate(project.getStartDate());
        }
        return ResponseEntity.ok(existingProject);
    }

    @Transactional
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable(name = "projectId") UUID projectId) {
        Project existingProject = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        projectRepository.delete(existingProject);
        return new ResponseEntity<>(existingProject, HttpStatus.NO_CONTENT);
    }
    @Transactional
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable(name = "projectId") UUID projectId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        return ResponseEntity.ok(project.getTasks());
    }
    @Transactional
    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<Task> createTaskForProject(@PathVariable(name = "projectId") UUID projectId, @RequestBody Task task) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        Task taskToBeSaved = Task.builder()
                .name(task.getName())
                .endDate(task.getEndDate())
                .description(task.getDescription())
                .status(task.getStatus())
                .project(project)
                .build();
        taskRepository.save(taskToBeSaved);
        return ResponseEntity.ok(taskToBeSaved);
    }

    @Transactional
    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> getTasksByProjectIdAndTaskId(@PathVariable(name = "projectId") UUID projectId,@PathVariable(name = "taskId") UUID taskId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        Task task1 = project.getTasks().stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " is not found"));
        return ResponseEntity.ok(task1);
    }
    @Transactional
    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable(name = "projectId") UUID projectId,@PathVariable(name = "taskId") UUID taskId, @RequestBody Task task) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        Task task1 = project.getTasks().stream()
                .filter(localTask -> localTask.getTaskId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " is not found"));
        if (!task1.equals(task)){
            task1.setName(task.getName());
            task1.setStatus(task.getStatus());
            task1.setDescription(task.getDescription());
            task1.setEndDate(task.getEndDate());
        }
        return ResponseEntity.ok(task1);
    }

    @Transactional
    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable(name = "projectId") UUID projectId,@PathVariable(name = "taskId") UUID taskId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id " + projectId + " was not found"));
        Task task1 = project.getTasks().stream()
                .filter(localTask -> localTask.getTaskId().equals(taskId))
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " is not found"));
        taskRepository.delete(task1);
        return ResponseEntity.ok(task1);
    }
}
