package ru.aliev.rgr.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.aliev.rgr.entity.University;
import ru.aliev.rgr.util.Extractors;

@Service
public class UniversityService {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<University> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM University", new BeanPropertyRowMapper<>(University.class));
  }

  public University findById(UUID id) {
    return jdbcTemplate.query(
        """
                    SELECT
                            u.id AS university_id, u.name AS university_name, u.address AS university_address,
                            s.id AS speciality_id, s.name AS speciality_name,
                            g.id AS graduate_id, g.first_name AS graduate_first_name,
                            g.last_name AS graduate_last_name, g.middle_name AS graduate_middle_name
                    FROM University u
                    LEFT JOIN Speciality s ON u.id = s.university_id
                    LEFT JOIN Graduate g ON s.id = g.speciality_id
                    WHERE u.id = ?
        """,
        new Object[] {id},
        new Extractors.UniversityExtractor());
  }

  public void save(University university) {
    jdbcTemplate.update(
        "INSERT INTO University(id, name, address) VALUES(?, ?, ?)",
        UUID.randomUUID(),
        university.getName(),
        university.getAddress());
  }

  public void update(UUID id, University updatedUniversity) {
    jdbcTemplate.update(
        "UPDATE University SET name=?, address=? WHERE id=?",
        updatedUniversity.getName(),
        updatedUniversity.getAddress(),
        id);
  }

  public void delete(UUID id) {
    jdbcTemplate.update("DELETE FROM University WHERE id=?", id);
  }

  public void deleteSpeciality(UUID id) {
    jdbcTemplate.update("UPDATE Speciality SET university_id = null WHERE id=?", id);
  }

  public void deleteGraduate(UUID id) {
    jdbcTemplate.update("UPDATE Graduate SET speciality_id = null WHERE id=?", id);
  }
}
