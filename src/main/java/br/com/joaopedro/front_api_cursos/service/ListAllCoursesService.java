package br.com.joaopedro.front_api_cursos.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.joaopedro.front_api_cursos.dto.CourseDTO;

@Service
public class ListAllCoursesService {
 public List<CourseDTO> execute(String token, String category) {
  RestTemplate rt = new RestTemplate();
        
  // Criação dos headers e adição do token no cabeçalho
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth(token);
        
  // Criando o HttpEntity com os headers (sem corpo)
  HttpEntity<Void> entity = new HttpEntity<>(headers);
        
  // Definição do tipo de resposta
  ParameterizedTypeReference<List<CourseDTO>> responseType = new ParameterizedTypeReference<List<CourseDTO>>() {};
        
  // Enviando a requisição para a API
  ResponseEntity<List<CourseDTO>> result = rt.exchange(
   "http://localhost:8080/cursos",     // URL da API
   HttpMethod.GET,                     // Método HTTP GET (sem corpo)
   entity,                             // Corpo da requisição com os headers
   responseType                        // Tipo de resposta esperado
   );
        
  // Retorna o corpo da resposta
  return result.getBody();
 }
}
