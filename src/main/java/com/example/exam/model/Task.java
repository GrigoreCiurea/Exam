package com.example.exam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "tasks")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_id")
    private UUID taskId;
    @Column(name = "name")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_Id")
    private Project project;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
