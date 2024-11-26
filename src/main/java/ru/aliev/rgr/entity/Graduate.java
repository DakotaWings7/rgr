package ru.aliev.rgr.entity;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Graduate {

  private UUID id;

  private UUID specialityId;

  private String firstName;

  private String lastName;

  private String middleName;

  private int graduateYear;

  private List<Employment> employments;
}
