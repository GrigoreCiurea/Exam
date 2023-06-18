package com.example.exam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "projects")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID projectId;
    @Column(name = "name")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Task> tasks = new ArrayList<>();
}
