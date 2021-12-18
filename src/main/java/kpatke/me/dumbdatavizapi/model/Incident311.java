package kpatke.me.dumbdatavizapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Incident311 {
  private String category;
  private LocalDate incidentDate;
}
