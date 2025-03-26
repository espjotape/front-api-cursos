package br.com.joaopedro.front_api_cursos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import br.com.joaopedro.front_api_cursos.service.ListAllCoursesService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

 @Autowired
 private ListAllCoursesService listAllCoursesService;
 
 @GetMapping()
 public String list(Model model){
  var result = this.listAllCoursesService.execute(getToken(), null);
  model.addAttribute("courses", result);
  System.out.println(result);
  return "home";
 }

 @GetMapping("/create")
 public String addCourse() {
  return "create";
 }

 private String getToken(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  return authentication.getDetails().toString();
 }
}
