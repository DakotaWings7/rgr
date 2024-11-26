package ru.aliev.rgr.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.aliev.rgr.entity.Admission;

@Service
public class AdmissionService {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Admission> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM Admission", new BeanPropertyRowMapper<>(Admission.class));
  }

  public Admission findById(UUID id) {
    return jdbcTemplate
        .query(
            "SELECT * FROM Admission a WHERE a.id=?",
            new Object[] {id},
            new BeanPropertyRowMapper<>(Admission.class))
        .stream()
        .findAny()
        .orElse(null);
  }

  public void save(Admission admission) {
    jdbcTemplate.update(
        "INSERT INTO Admission(id, speciality_id, admission_year, students_count) "
            + "VALUES(?, ?, ?, ?)",
        UUID.randomUUID(),
        admission.getSpecialityId(),
        admission.getAdmissionYear(),
        admission.getStudentsCount());
  }

  public void update(UUID id, Admission updatedAdmission) {
    jdbcTemplate.update(
        "UPDATE Admission SET speciality_id=?, admission_year=?, students_count=? WHERE id=?",
        updatedAdmission.getSpecialityId(),
        updatedAdmission.getAdmissionYear(),
        updatedAdmission.getStudentsCount(),
        id);
  }

  public void delete(UUID id) {
    jdbcTemplate.update("DELETE FROM Admission WHERE id=?", id);
  }
}
