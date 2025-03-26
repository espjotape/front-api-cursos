package br.com.joaopedro.front_api_cursos.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.joaopedro.front_api_cursos.dto.CreateCourseDTO;

@Service
public class CreateCourseService {
 public String execute(CreateCourseDTO course, String token) {
  RestTemplate rt = new RestTemplate();

  HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_JSON);
  headers.setBearerAuth(token);

  HttpEntity<CreateCourseDTO> request = new HttpEntity<>(course , headers);

  var result = rt.postForObject("http://localhost:8080/cursos", request, String.class);
  return result;
 }
}
