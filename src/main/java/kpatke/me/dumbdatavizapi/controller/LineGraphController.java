package kpatke.me.dumbdatavizapi.controller;

import kpatke.me.dumbdatavizapi.model.LineGraphData;
import kpatke.me.dumbdatavizapi.model.Request311Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineGraphController {

  @GetMapping("/line-graph")
  public LineGraphData test() {
    var data = new LineGraphData();

    return data;
  }

  private List<Request311Record> get311Data() {

  }
}