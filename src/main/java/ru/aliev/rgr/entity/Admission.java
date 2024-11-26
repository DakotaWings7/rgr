package ru.aliev.rgr.entity;

import java.util.UUID;
import lombok.Data;

@Data
public class Admission {

  private UUID id;

  private UUID specialityId;

  private int admissionYear;

  private int studentsCount;
}
