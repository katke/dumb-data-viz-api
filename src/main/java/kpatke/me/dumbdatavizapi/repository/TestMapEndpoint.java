package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.TestMapData;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Configuration
public class TestMapEndpoint {

  @GetMapping("/test")
  public TestMapData test() {
    var data = new TestMapData();
    data.setLatitude("43.000");
    data.setLongitude("-128.983");
    data.setName("test");
    return data;
  }
}