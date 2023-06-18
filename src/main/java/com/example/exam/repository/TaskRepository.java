package com.example.exam.repository;

import com.example.exam.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByTaskId(UUID taskId);
}
