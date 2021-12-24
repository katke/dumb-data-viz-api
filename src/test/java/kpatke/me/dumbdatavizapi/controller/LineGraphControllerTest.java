package kpatke.me.dumbdatavizapi.controller;

import kpatke.me.dumbdatavizapi.model.Incident311;
import kpatke.me.dumbdatavizapi.model.Request311Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineGraphControllerTest {

  LineGraphController controller = new LineGraphController();

  @Test
  void remapDataTest() {
    var inputData = generateTestData();
    var expected = List.of(new Incident311("SNOW", LocalDate.of(2021, 03, 05)));
    var actual = controller.remapData(inputData);
    assertEquals(1, actual.size());
    assertAll("Field values match expected", () -> {
      var expectedObj = expected.get(0);
      var actualObj = actual.get(0);
      assertEquals(expectedObj.getCategory(), actualObj.getCategory());
      assertEquals(expectedObj.getIncidentDate(), actualObj.getIncidentDate());
    });
  }

  static List<Request311Record> generateTestData() {
    var record1 = new Request311Record();
    record1.setCreatedDate("2021-03-05T11:32:29.000");
    record1.setRequestType("SNOW");
    var missingCreatedDateRecord = new Request311Record();
    missingCreatedDateRecord.setRequestType("SIDEWALK_CAFE");
    return List.of(record1, missingCreatedDateRecord);

  }

}
