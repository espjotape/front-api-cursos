package br.com.joaopedro.front_api_cursos.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import br.com.joaopedro.front_api_cursos.dto.CourseDTO;
import br.com.joaopedro.front_api_cursos.dto.CreateCourseDTO;
import br.com.joaopedro.front_api_cursos.service.CreateCourseService;
import br.com.joaopedro.front_api_cursos.service.DeleteCourseService;
import br.com.joaopedro.front_api_cursos.service.ListAllCoursesService;
import br.com.joaopedro.front_api_cursos.service.SearchCourseService;
import br.com.joaopedro.front_api_cursos.service.UpdateCourseService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

 @Autowired
 private ListAllCoursesService listAllCoursesService;
 
 @Autowired CreateCourseService createCourseService;

 @Autowired
 private SearchCourseService searchCourseService;

 @Autowired
 private DeleteCourseService deleteCourseService;

 @Autowired 
 private UpdateCourseService updateCourseService;

 @GetMapping("/home")
 public String list(Model model){
  var result = this.listAllCoursesService.execute(getToken(), null);
  model.addAttribute("courses", result);
  System.out.println(result);
  return "home";
 }

 @GetMapping("/create")
 public String addCourse(Model model) {
  model.addAttribute("course", new CourseDTO());
  return "create";
 }

 @PostMapping("/create")
 public String createCourse(@ModelAttribute CreateCourseDTO course){
  var result = this.createCourseService.execute(course, getToken());
  System.out.println(result);
  return "redirect:/cursos/home";
 }
 
 @GetMapping("/details/{id}")
 public String getCourseDetails(@PathVariable UUID id, Model model) {
  CourseDTO course = searchCourseService.searchCourseById(id);

  if (course == null) {
   // Caso o curso não seja encontrado, você pode redirecionar para uma página de erro ou mostrar uma mensagem
   model.addAttribute("error", "Curso não encontrado.");
   return "error";
}

  model.addAttribute("course", course);

  System.out.println(course);
  
  return "details";
 }

 @PostMapping("/delete/{id}")
 public String deleteCourse(@PathVariable UUID id) {
     try {
         // Chama o serviço para excluir o curso
         deleteCourseService.execute(id);
         return "redirect:/cursos/home";
     } catch (Exception e) {
         e.printStackTrace();
         return "error";
     }
 }
 
 @GetMapping("/edit/{id}")
 public String PageEdit(@PathVariable UUID id, Model model) {
    CourseDTO course = searchCourseService.searchCourseById(id);
    model.addAttribute("course", course);
    return "edit";
 }

 @PostMapping("/edit/{id}")
 public String editCourse(@PathVariable UUID id, Model model, CourseDTO course) {
    updateCourseService.execute(course, getToken(), id);
    return "redirect:/cursos/home";
 }

 private String getToken(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  return authentication.getDetails().toString();
 }
}
