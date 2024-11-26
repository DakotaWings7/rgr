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
import ru.aliev.rgr.entity.Graduate;
import ru.aliev.rgr.service.EmploymentService;
import ru.aliev.rgr.service.GraduateService;
import ru.aliev.rgr.service.SpecialityService;

@Controller
@AllArgsConstructor
@RequestMapping("/graduates")
public class GraduateController {

  private final GraduateService service;
  private final SpecialityService specialityService;
  private final EmploymentService employmentService;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("graduates", service.findAll());
    return "graduate/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") UUID id, Model model) {
    Graduate graduate = service.findById(id);
    model.addAttribute("graduate", graduate);
    model.addAttribute("speciality", specialityService.findById(graduate.getSpecialityId()));
    return "graduate/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("graduate") Graduate graduate, Model model) {
    model.addAttribute("specialities", specialityService.findAll());
    return "graduate/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("graduate") Graduate graduate) {
    service.save(graduate);
    return "redirect:/graduates";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") UUID id) {
    model.addAttribute("graduate", service.findById(id));
    model.addAttribute("specialities", specialityService.findAll());

    return "graduate/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("graduate") Graduate graduate, @PathVariable("id") UUID id) {

    service.update(id, graduate);
    return "redirect:/graduates/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") UUID id) {
    service.delete(id);
    return "redirect:/graduates";
  }
}
