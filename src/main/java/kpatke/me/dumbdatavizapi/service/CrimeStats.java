package kpatke.me.dumbdatavizapi.service;

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

  private @Value("${data.endpoints.crime}") String endpoint;
  private final HttpMethod method = HttpMethod.GET;

  RestTemplate setupRestTemplate() {
    return new RestTemplateBuilder()
    .rootUri(endpoint)
    .build();
  }

  public void makeRequest() {
    var restTemplate = this.setupRestTemplate();
    var responseEntity = restTemplate.getForEntity(this.endpoint, CrimeRecord[].class);
    logger.info(responseEntity.getStatusCode().toString());
    if (responseEntity.getBody() != null) {
      var arbitraryFirstRecord = Arrays.stream(responseEntity.getBody()).findFirst();
      arbitraryFirstRecord.ifPresent(crimeRecord -> logger.info(crimeRecord.toString()));
    } else {
      logger.info("Response body is null");
    }
  }


}
