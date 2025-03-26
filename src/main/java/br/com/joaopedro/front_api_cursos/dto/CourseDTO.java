package br.com.joaopedro.front_api_cursos.dto;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CourseDTO {
 private UUID id;
 private String name;
 private String category;
 private String status;
 private String teacherName;
 private LocalDateTime createdAt;
 private LocalDateTime updatedAt;
}
