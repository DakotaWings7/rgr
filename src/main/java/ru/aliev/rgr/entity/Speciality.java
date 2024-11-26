package ru.aliev.rgr.entity;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Speciality {

  private UUID id;

  private UUID universityId;

  private Integer educationYears;

  private String name;

  private List<Admission> admissions;
}
