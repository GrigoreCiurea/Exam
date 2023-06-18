package com.example.exam.repository;

import com.example.exam.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByProjectId(UUID projectId);
}
