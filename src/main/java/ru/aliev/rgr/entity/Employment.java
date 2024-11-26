package ru.aliev.rgr.entity;

import java.util.UUID;
import lombok.Data;

@Data
public class Employment {

  private UUID id;

  private UUID graduateId;

  private String companyName;

  private String position;

  private Integer salary;
}
