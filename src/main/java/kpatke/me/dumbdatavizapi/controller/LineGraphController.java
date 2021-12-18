package kpatke.me.dumbdatavizapi.controller;

import kpatke.me.dumbdatavizapi.model.Incident311;
import kpatke.me.dumbdatavizapi.model.Request311Record;
import kpatke.me.dumbdatavizapi.repository.Requests311;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LineGraphController {
  @Autowired
  Requests311 lineGraph311Request;

  @GetMapping("/line-graph")
  public List<Incident311> test() {
    var records = get311Data();
    return remapData(records);
  }

  private List<Request311Record> get311Data() {
    return lineGraph311Request.makeRequest();
  }

  private List<Incident311> remapData(List<Request311Record> rawData) {
    return rawData.stream()
        .filter(Request311Record::isLineGraphValidData)
        .map(record -> {
          // raw format 2021-03-05T11:32:29.000
          var parsedDateTime = LocalDateTime.parse(record.getCreatedDate());
          return new Incident311(record.getRequestType(), parsedDateTime.toLocalDate());
        })
        .collect(Collectors.toList());
  }
}