package kpatke.me.dumbdatavizapi;

import kpatke.me.dumbdatavizapi.repository.CrimeStats;
import kpatke.me.dumbdatavizapi.repository.Requests311;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(Runner.class);

  @Autowired
  CrimeStats crimeStats;
  @Autowired
  Requests311 requestsTo311;

  public static void main(String[] args) {
    SpringApplication.run(Runner.class, args);
  }

  public void run(String ...input) {
    try {
      crimeStats.makeRequest();
      requestsTo311.makeRequest();
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
