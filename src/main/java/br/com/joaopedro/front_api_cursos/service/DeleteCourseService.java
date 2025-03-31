package br.com.joaopedro.front_api_cursos.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeleteCourseService {

 private final String BASE_URL = "http://localhost:8080/cursos";

 public void execute(UUID id) {
     String url = BASE_URL + "/" + id.toString(); // Converte UUID para String
        
     RestTemplate restTemplate = new RestTemplate();

     // Configura os cabeçalhos
     HttpHeaders headers = new HttpHeaders();
     headers.setContentType(MediaType.APPLICATION_JSON);

     // Prepara a entidade HTTP com cabeçalhos
     HttpEntity<String> entity = new HttpEntity<>(headers);

     try {
      restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
     } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao deletar curso com ID: " + id);
     }
 }
}
