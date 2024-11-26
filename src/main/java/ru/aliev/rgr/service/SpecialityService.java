package ru.aliev.rgr.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.aliev.rgr.entity.Speciality;
import ru.aliev.rgr.util.Extractors;

@Service
public class SpecialityService {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Speciality> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM Speciality", new BeanPropertyRowMapper<>(Speciality.class));
  }

  public Speciality findById(UUID id) {
    return jdbcTemplate.query(
        """
                        SELECT
                            s.id AS speciality_id, s.name AS speciality_name,
                            s.education_years AS speciality_education_years,
                            ad.id AS admission_id,
                            ad.admission_year AS admission_admission_year,
                            ad.students_count AS admission_students_count
                        FROM Speciality s
                        LEFT JOIN Admission ad ON s.id = ad.speciality_id
                        WHERE s.id=?
               """,
        new Object[] {id},
        new Extractors.SpecialityExtractor());
  }

  public void save(Speciality speciality) {
    jdbcTemplate.update(
        "INSERT INTO Speciality(id, university_id, name, education_years) VALUES(?, ?, ?, ?)",
        UUID.randomUUID(),
        speciality.getUniversityId(),
        speciality.getName(),
        speciality.getEducationYears());
  }

  public void update(UUID id, Speciality updatedSpeciality) {
    jdbcTemplate.update(
        "UPDATE Speciality SET name=?, university_id=?, education_years=? WHERE id=?",
        updatedSpeciality.getName(),
        updatedSpeciality.getUniversityId(),
        updatedSpeciality.getEducationYears(),
        id);
  }

  public void delete(UUID id) {
    jdbcTemplate.update("DELETE FROM Speciality WHERE id=?", id);
  }
}
