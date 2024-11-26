package ru.aliev.rgr.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aliev.rgr.entity.Employment;
import ru.aliev.rgr.service.EmploymentService;
import ru.aliev.rgr.service.GraduateService;

@Controller
@AllArgsConstructor
@RequestMapping("/employments")
public class EmploymentController {

  private final EmploymentService service;
  private final GraduateService graduateService;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("employments", service.findAll());
    model.addAttribute("graduateService", graduateService);
    return "employment/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") UUID id, Model model) {
    Employment employment = service.findById(id);
    model.addAttribute("employment", employment);
    model.addAttribute("graduate", graduateService.findById(employment.getGraduateId()));
    return "employment/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("employment") Employment employment, Model model) {
    model.addAttribute("graduates", graduateService.findAll());
    return "employment/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("employment") Employment employment) {
    service.save(employment);
    return "redirect:/employments";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") UUID id) {
    model.addAttribute("employment", service.findById(id));
    model.addAttribute("graduates", graduateService.findAll());

    return "employment/edit";
  }

  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("employment") Employment employment, @PathVariable("id") UUID id) {

    service.update(id, employment);
    return "redirect:/employments/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") UUID id) {
    service.delete(id);
    return "redirect:/employments";
  }
}
