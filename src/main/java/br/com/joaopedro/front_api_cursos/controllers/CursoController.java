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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import br.com.joaopedro.front_api_cursos.dto.CourseDTO;
import br.com.joaopedro.front_api_cursos.dto.CreateCourseDTO;
import br.com.joaopedro.front_api_cursos.service.ActiveCourseService;
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

 @Autowired 
 private ActiveCourseService activeCourseService;

 @GetMapping("/home")
 public String list(Model model){
  var result = this.listAllCoursesService.execute(getToken(), null);
  model.addAttribute("courses", result);
  return "home";
 }

 @GetMapping("/create")
 public String addCourse(Model model) {
  model.addAttribute("course", new CourseDTO());
  return "create";
 }

 @PostMapping("/create")
  public String createCourse(@ModelAttribute CreateCourseDTO course, RedirectAttributes redirectAttributes) {
    try {
        if (course.getName() == null || course.getName().trim().isEmpty() ||
            course.getCategory() == null || course.getCategory().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_message", "Course name and category are required fields");
            return "redirect:/cursos/create";
        }

        var result = this.createCourseService.execute(course, getToken());
        return "redirect:/cursos/home";
        
    } catch (HttpClientErrorException e) {
        redirectAttributes.addFlashAttribute("error_message", "Error creating course. Please try again.");
        return "redirect:/cursos/create";
    }
}
 
 @GetMapping("/details/{id}")
 public String getCourseDetails(@PathVariable UUID id, Model model) {
  CourseDTO course = searchCourseService.searchCourseById(id);

  if (course == null) {
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
 public String editCourse(@PathVariable UUID id, Model model, CourseDTO course, RedirectAttributes redirectAttributes) {
    try {
        if (course.getName() == null || course.getName().trim().isEmpty() ||
            course.getCategory() == null || course.getCategory().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_message", "Por favor, preencha os campos de nome do curso e a categoria");
            return "redirect:/cursos/edit/" + id;
        }
        updateCourseService.execute(course, getToken(), id);
        return "redirect:/cursos/home";

    } catch (HttpClientErrorException e) {
        redirectAttributes.addFlashAttribute("error_message", "Erro ao atualizar curso. Tente novamente.");
        return "redirect:/cursos/edit/" + id;
    }
}

 @PostMapping("/active/{id}")
 public String toggleCurso(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
   activeCourseService.toggleCourseStatus(id);
   return "redirect:/cursos/home";
 }

 private String getToken(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  return authentication.getDetails().toString();
 }
}
