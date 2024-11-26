package ru.aliev.rgr.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.aliev.rgr.entity.Admission;
import ru.aliev.rgr.entity.Employment;
import ru.aliev.rgr.entity.Graduate;
import ru.aliev.rgr.entity.Speciality;
import ru.aliev.rgr.entity.University;

@Component
public class Extractors {

  public static class UniversityExtractor implements ResultSetExtractor<University> {
    @Override
    public University extractData(ResultSet rs) throws SQLException {
      University university = null;
      Map<UUID, Speciality> specialityMap = new LinkedHashMap<>();
      Map<UUID, Graduate> graduateMap = new LinkedHashMap<>();

      while (rs.next()) {
        if (university == null) {
          university = new University();
          university.setId(rs.getObject("university_id", UUID.class));
          university.setName(rs.getString("university_name"));
          university.setAddress(rs.getString("university_address"));
          university.setSpecialities(new ArrayList<>());
          university.setGraduates(new ArrayList<>());
        }

        UUID specialityId = rs.getObject("speciality_id", UUID.class);
        if (specialityId != null && !specialityMap.containsKey(specialityId)) {
          Speciality speciality = new Speciality();
          speciality.setId(specialityId);
          speciality.setName(rs.getString("speciality_name"));
          speciality.setUniversityId(university.getId());
          specialityMap.put(specialityId, speciality);
        }

        UUID graduateId = rs.getObject("graduate_id", UUID.class);
        if (graduateId != null && !specialityMap.containsKey(graduateId)) {
          Graduate graduate = new Graduate();
          graduate.setId(graduateId);
          graduate.setFirstName(rs.getString("graduate_first_name"));
          graduate.setLastName(rs.getString("graduate_last_name"));
          graduate.setMiddleName(rs.getString("graduate_middle_name"));
          graduateMap.put(graduateId, graduate);
        }
      }

      if (university != null) {
        university.getSpecialities().addAll(specialityMap.values());
        university.getGraduates().addAll(graduateMap.values());
      }

      return university;
    }
  }

  public static class GraduateExtractor implements ResultSetExtractor<Graduate> {

    @Override
    public Graduate extractData(ResultSet rs) throws SQLException {
      Graduate graduate = null;
      Map<UUID, Employment> employmentMap = new LinkedHashMap<>();

      while (rs.next()) {
        if (graduate == null) {
          graduate = new Graduate();
          graduate.setId(rs.getObject("graduate_id", UUID.class));
          graduate.setFirstName(rs.getString("graduate_first_name"));
          graduate.setLastName(rs.getString("graduate_last_name"));
          graduate.setMiddleName(rs.getString("graduate_middle_name"));
          graduate.setGraduateYear(rs.getInt("graduate_graduate_year"));
          graduate.setSpecialityId(rs.getObject("graduate_speciality_id", UUID.class));
          graduate.setEmployments(new ArrayList<>());
        }

        UUID employmentId = rs.getObject("employment_id", UUID.class);
        if (employmentId != null && !employmentMap.containsKey(employmentId)) {
          Employment employment = new Employment();
          employment.setId(employmentId);
          employment.setGraduateId(graduate.getId());
          employment.setCompanyName(rs.getString("employment_company_name"));
          employment.setPosition(rs.getString("employment_position"));
          employment.setSalary(rs.getInt("employment_salary"));
          employmentMap.put(employmentId, employment);
        }
      }

      if (graduate != null) {
        graduate.getEmployments().addAll(employmentMap.values());
      }

      return graduate;
    }
  }

  public static class SpecialityExtractor implements ResultSetExtractor<Speciality> {

    @Override
    public Speciality extractData(ResultSet rs) throws SQLException {
      Speciality speciality = null;
      Map<UUID, Admission> admissionMap = new LinkedHashMap<>();

      while (rs.next()) {
        if (speciality == null) {
          speciality = new Speciality();
          speciality.setId(rs.getObject("speciality_id", UUID.class));
          speciality.setEducationYears(rs.getInt("speciality_education_years"));
          speciality.setName(rs.getString("speciality_name"));
          speciality.setAdmissions(new ArrayList<>());
        }

        UUID admissionId = rs.getObject("admission_id", UUID.class);
        if (admissionId != null && !admissionMap.containsKey(admissionId)) {
          Admission admission = new Admission();
          admission.setId(admissionId);
          admission.setAdmissionYear(rs.getInt("admission_admission_year"));
          admission.setStudentsCount(rs.getInt("admission_students_count"));
          admissionMap.put(admissionId, admission);
        }
      }

      if (speciality != null) {
        speciality.getAdmissions().addAll(admissionMap.values());
      }

      return speciality;
    }
  }
}
