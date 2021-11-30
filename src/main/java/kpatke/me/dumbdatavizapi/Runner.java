package kpatke.me.dumbdatavizapi;

import kpatke.me.dumbdatavizapi.service.CrimeStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Runner implements CommandLineRunner {

  @Autowired
  CrimeStats crimeStats;

  public static void main(String[] args) {
    SpringApplication.run(Runner.class, args);
  }

  public void run(String ...input) {
    try {
      crimeStats.makeRequest();
      System.exit(0);
    } catch(Exception ex) {
      // TODO set up proper logger
      System.out.println("Error: " + ex.getMessage());
      System.exit(1);
    }
  }

//  @Bean
//  TestConsumer crimeStatsClient(CrimeStats crimeStatsRequest) {
//    return new TestConsumer(crimeStatsRequest);
//  }

}
