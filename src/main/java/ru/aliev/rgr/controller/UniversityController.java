package ru.aliev.rgr.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aliev.rgr.entity.University;
import ru.aliev.rgr.service.UniversityService;

@Controller
@AllArgsConstructor
@RequestMapping("/universities")
public class UniversityController {

  private final UniversityService service;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("universities", service.findAll());
    return "university/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") UUID id, Model model) {
    model.addAttribute("university", service.findById(id));
    return "university/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("university") University university) {
    return "university/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("university") University university) {
    service.save(university);
    return "redirect:/universities";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") UUID id) {
    model.addAttribute("university", service.findById(id));
    return "university/edit";
  }

  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("university") University university, @PathVariable("id") UUID id) {

    service.update(id, university);
    return "redirect:/universities/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") UUID id) {
    service.delete(id);
    return "redirect:/universities";
  }

  @DeleteMapping("/{id}/specialities/{speciality-id}")
  public String deleteSpeciality(
      @PathVariable("id") UUID id, @PathVariable("speciality-id") UUID specialityId) {
    service.deleteSpeciality(specialityId);
    return "redirect:/universities/" + id;
  }

  @DeleteMapping("/{id}/graduates/{graduate-id}")
  public String deleteGraduate(
      @PathVariable("id") UUID id, @PathVariable("graduate-id") UUID graduateId) {
    service.deleteGraduate(graduateId);
    return "redirect:/universities/" + id;
  }
}
