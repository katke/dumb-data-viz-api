package kpatke.me.dumbdatavizapi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LineGraphData {
  List<Incident> data;

  class Incident {
    private String category;
    private LocalDate incidentDate;
  }
}
