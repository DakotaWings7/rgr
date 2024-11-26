package ru.aliev.rgr.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.aliev.rgr.entity.Graduate;
import ru.aliev.rgr.util.Extractors;

@Service
public class GraduateService {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Graduate> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM Graduate", new BeanPropertyRowMapper<>(Graduate.class));
  }

  public Graduate findById(UUID id) {
    return jdbcTemplate.query(
        """
                    SELECT g.id AS graduate_id, g.first_name AS graduate_first_name,
                           g.last_name AS graduate_last_name, g.middle_name AS graduate_middle_name,
                           g.graduate_year AS graduate_graduate_year, g.speciality_id AS graduate_speciality_id,
                           e.id AS employment_id, e.company_name AS employment_company_name,
                           e.position AS employment_position, e.salary AS employment_salary
                    FROM Graduate g
                    LEFT JOIN Employment e ON g.id = e.graduate_id
                    WHERE g.id=?
        """,
        new Object[] {id},
        new Extractors.GraduateExtractor());
  }

  public void save(Graduate graduate) {
    jdbcTemplate.update(
        "INSERT INTO Graduate(id, first_name, last_name, middle_name, graduate_year, speciality_id) "
            + "VALUES(?, ?, ?, ?, ?, ?)",
        UUID.randomUUID(),
        graduate.getFirstName(),
        graduate.getLastName(),
        graduate.getMiddleName(),
        graduate.getGraduateYear(),
        graduate.getSpecialityId());
  }

  public void update(UUID id, Graduate updatedGraduate) {
    jdbcTemplate.update(
        "UPDATE Graduate SET first_name=?, last_name=?, middle_name=?, graduate_year=?, speciality_id=? WHERE id=?",
        updatedGraduate.getFirstName(),
        updatedGraduate.getLastName(),
        updatedGraduate.getMiddleName(),
        updatedGraduate.getGraduateYear(),
        updatedGraduate.getSpecialityId(),
        id);
  }

  public void delete(UUID id) {
    jdbcTemplate.update("DELETE FROM Graduate WHERE id=?", id);
  }
}
