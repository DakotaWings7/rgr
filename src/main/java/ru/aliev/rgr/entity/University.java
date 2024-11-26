package ru.aliev.rgr.entity;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class University {

  private UUID id;

  private String name;

  private String address;

  private List<Speciality> specialities;

  private List<Graduate> graduates;
}
