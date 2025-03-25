package br.com.joaopedro.front_api_cursos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cursos")
public class CursoController {
 
 @GetMapping("/home")
 public String home() {
  return "home";
 }
}
