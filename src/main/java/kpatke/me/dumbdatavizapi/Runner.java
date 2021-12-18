package kpatke.me.dumbdatavizapi;

import kpatke.me.dumbdatavizapi.model.CrimeRecord;
import kpatke.me.dumbdatavizapi.model.Request311Record;
import kpatke.me.dumbdatavizapi.repository.CrimeStats;
import kpatke.me.dumbdatavizapi.repository.Requests311;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

import static kpatke.me.dumbdatavizapi.repository.Requests311.ShortCodeType.*;

@SpringBootApplication
public class Runner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(Runner.class);

  @Autowired
  CrimeStats crimeStats;

  public static void main(String[] args) {
    SpringApplication.run(Runner.class, args);
  }

  public void run(String ...input) {
    try {
//      var crimeResults = crimeStats.makeRequest()
//          .stream()
//          .filter(CrimeRecord::isUsableRecord)
//          .collect(Collectors.toList());
      var request311 = new Requests311(List.of(
          ICE_SNOW_REMOVAL, UNCLEARED_SNOW_SIDEWALK, SIDEWALK_CAFE_COMPLAINT,
          BEE_WASP_REMOVAL, COYOTE_INTERACTION));
      request311.makeRequest();
      System.exit(0);
    } catch(Exception ex) {
      logger.error("Error: " + ex.getMessage());
      System.exit(1);
    }
  }

//  @Bean
//  TestConsumer crimeStatsClient(CrimeStats crimeStatsRequest) {
//    return new TestConsumer(crimeStatsRequest);
//  }

}
