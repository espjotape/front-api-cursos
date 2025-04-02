package br.com.joaopedro.front_api_cursos.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActiveCourseService {

    private final String BASE_URL = "http://localhost:8080/cursos";

    public void toggleCourseStatus(UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        
        String url = BASE_URL + "/" + id + "/active";

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            System.out.println("Resposta do backend: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao alternar status do curso.");
        }
    }
}