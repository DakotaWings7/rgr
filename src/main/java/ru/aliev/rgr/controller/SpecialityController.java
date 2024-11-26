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
import ru.aliev.rgr.entity.Speciality;
import ru.aliev.rgr.service.SpecialityService;
import ru.aliev.rgr.service.UniversityService;

@Controller
@AllArgsConstructor
@RequestMapping("/specialities")
public class SpecialityController {

  private final SpecialityService service;
  private final UniversityService universityService;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("specialities", service.findAll());
    model.addAttribute("universityService", universityService);
    return "speciality/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") UUID id, Model model) {
    Speciality speciality = service.findById(id);
    model.addAttribute("speciality", speciality);
    model.addAttribute("university", universityService.findById(speciality.getUniversityId()));
    return "speciality/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("speciality") Speciality speciality, Model model) {
    model.addAttribute("universities", universityService.findAll());
    return "speciality/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("speciality") Speciality speciality) {
    service.save(speciality);
    return "redirect:/specialities";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") UUID id) {
    model.addAttribute("speciality", service.findById(id));
    model.addAttribute("universities", universityService.findAll());

    return "speciality/edit";
  }

  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("speciality") Speciality speciality, @PathVariable("id") UUID id) {

    service.update(id, speciality);
    return "redirect:/specialities/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") UUID id) {
    service.delete(id);
    return "redirect:/specialities";
  }
}
