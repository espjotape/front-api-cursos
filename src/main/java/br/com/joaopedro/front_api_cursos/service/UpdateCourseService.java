package br.com.joaopedro.front_api_cursos.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.joaopedro.front_api_cursos.dto.CourseDTO;

@Service
public class UpdateCourseService {
 public String execute(CourseDTO course, String token, UUID courseId) {
  RestTemplate rt = new RestTemplate();

  HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_JSON);
  headers.setBearerAuth(token); 

  HttpEntity<CourseDTO> request = new HttpEntity<>(course, headers);

  String url = "http://localhost:8080/cursos/" + courseId; 
        
  try {
   rt.exchange(url, HttpMethod.PUT, request, String.class);
   return "Curso atualizado com sucesso!";
  } catch (Exception e) {
   return "Erro ao atualizar curso: " + e.getMessage();
  }
}
}
