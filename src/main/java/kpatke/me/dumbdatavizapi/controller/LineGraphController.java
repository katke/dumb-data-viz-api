package kpatke.me.dumbdatavizapi.controller;

import kpatke.me.dumbdatavizapi.model.Incident311;
import kpatke.me.dumbdatavizapi.model.Request311Record;
import kpatke.me.dumbdatavizapi.repository.Requests311;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static kpatke.me.dumbdatavizapi.repository.Requests311.ShortCodeType.*;

@RestController
public class LineGraphController {

  @GetMapping("/line-graph")
  public List<Incident311> test() {
    var records = get311Data();
    return remapData(records);
  }

  private List<Request311Record> get311Data() {
    var request311 = new Requests311(List.of(
        ICE_SNOW_REMOVAL, UNCLEARED_SNOW_SIDEWALK, SIDEWALK_CAFE_COMPLAINT,
        BEE_WASP_REMOVAL, COYOTE_INTERACTION));
    return request311.makeRequest();
  }

  private List<Incident311> remapData(List<Request311Record> rawData) {
    return rawData.stream()
        .filter(Request311Record::isLineGraphValidData)
        .map(record -> {
          // raw format 2021-03-05T11:32:29.000
          var parsedDate = LocalDate.parse(record.getCreatedDate());
          return new Incident311(record.getRequestType(), parsedDate);
        })
        .collect(Collectors.toList());
  }
}