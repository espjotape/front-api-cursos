package br.com.joaopedro.front_api_cursos.dto;
import lombok.Data;

@Data
public class CreateCourseDTO {
 private String name;
 private String category;
 private String teacherName;
}
