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
public class SearchCourseService {

 private final String BASE_URL = "http://localhost:8080/cursos";

 // Injeta o RestTemplate como um bean
 public CourseDTO searchCourseById(UUID id) {
   String url = BASE_URL + "/" + id.toString(); // Garante que o UUID é convertido para String
   
   RestTemplate rt = new RestTemplate();

   // Configura os cabeçalhos
   HttpHeaders headers = new HttpHeaders();
   headers.setContentType(MediaType.APPLICATION_JSON);

   // Prepara a entidade HTTP com cabeçalhos
   HttpEntity<String> entity = new HttpEntity<>(headers);
        
   // Faz a requisição GET e retorna o corpo da resposta como um CourseDTO
   try {
     return rt.exchange(url, HttpMethod.GET, entity, CourseDTO.class).getBody();
   } catch (Exception e) {
     // Trate exceções adequadas, por exemplo, se o curso não for encontrado ou houver erro na comunicação
     e.printStackTrace();
     return null;
   }
 }
}
