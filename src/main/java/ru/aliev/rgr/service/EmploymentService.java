package ru.aliev.rgr.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.aliev.rgr.entity.Employment;

@Service
public class EmploymentService {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Employment> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM Employment", new BeanPropertyRowMapper<>(Employment.class));
  }

  public Employment findById(UUID id) {
    return jdbcTemplate
        .query(
            "SELECT * FROM Employment e WHERE e.id=?",
            new Object[] {id},
            new BeanPropertyRowMapper<>(Employment.class))
        .stream()
        .findAny()
        .orElse(null);
  }

  public void save(Employment employment) {
    jdbcTemplate.update(
        "INSERT INTO Employment(id, graduate_id, company_name, position, salary) "
            + "VALUES(?, ?, ?, ?, ?)",
        UUID.randomUUID(),
        employment.getGraduateId(),
        employment.getCompanyName(),
        employment.getPosition(),
        employment.getSalary());
  }

  public void update(UUID id, Employment updatedEmployment) {
    jdbcTemplate.update(
        "UPDATE Employment SET graduate_id=?, company_name=?, position=?, salary=? WHERE id=?",
        updatedEmployment.getGraduateId(),
        updatedEmployment.getCompanyName(),
        updatedEmployment.getPosition(),
        updatedEmployment.getSalary(),
        id);
  }

  public void delete(UUID id) {
    jdbcTemplate.update("DELETE FROM Employment WHERE id=?", id);
  }
}
