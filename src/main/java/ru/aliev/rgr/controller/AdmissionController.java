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
import ru.aliev.rgr.entity.Admission;
import ru.aliev.rgr.service.AdmissionService;
import ru.aliev.rgr.service.SpecialityService;
import ru.aliev.rgr.service.UniversityService;

@Controller
@AllArgsConstructor
@RequestMapping("/admissions")
public class AdmissionController {

  private final AdmissionService service;
  private final UniversityService universityService;
  private final SpecialityService specialityService;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("admissions", service.findAll());
    model.addAttribute("universityService", universityService);
    model.addAttribute("specialityService", specialityService);
    return "admission/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") UUID id, Model model) {
    model.addAttribute("admission", service.findById(id));
    model.addAttribute("universityService", universityService);
    model.addAttribute("specialityService", specialityService);
    return "admission/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("admission") Admission admission, Model model) {
    model.addAttribute("specialities", specialityService.findAll());
    return "admission/new";
  }

  @PostMapping
  public String create(@ModelAttribute("admission") Admission admission) {
    service.save(admission);
    return "redirect:/admissions";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") UUID id) {
    model.addAttribute("admission", service.findById(id));
    model.addAttribute("specialities", specialityService.findAll());
    return "admission/edit";
  }

  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("employment") Admission admission, @PathVariable("id") UUID id) {
    service.update(id, admission);
    return "redirect:/admissions/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") UUID id) {
    service.delete(id);
    return "redirect:/admissions";
  }
}
