package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.CrimeRecord;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Getter
@Component
public class CrimeStats implements ApiRequest {

  private static final Logger logger = LoggerFactory.getLogger(CrimeStats.class);

  private @Value("${data.endpoints.crime}") String fullEndpoint;
  private final HttpMethod method = HttpMethod.GET;

  public RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder()
    .rootUri(fullEndpoint)
    .build();
  }

  public void makeRequest() {
    var restTemplate = this.generateRestTemplate();
    var responseEntity = restTemplate.getForEntity(this.fullEndpoint, CrimeRecord[].class);
    logger.info(responseEntity.getStatusCode().toString());
    if (responseEntity.getBody() != null) {
      var arbitraryFirstRecord = Arrays.stream(responseEntity.getBody()).findFirst();
      arbitraryFirstRecord.ifPresent(crimeRecord -> logger.info(crimeRecord.toString()));
    } else {
      logger.info("Response body is null");
    }
  }


}
