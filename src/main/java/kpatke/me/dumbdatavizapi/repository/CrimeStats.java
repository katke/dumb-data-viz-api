package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.CrimeRecord;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CrimeStats implements ApiRequest {

  private static final Logger logger = LoggerFactory.getLogger(CrimeStats.class);

  @Getter private final String fullEndpoint;
  private final HttpMethod method = HttpMethod.GET;
  private final RestTemplate restTemplate;

  public CrimeStats(RestTemplate restTemplate, String endpoint) {
    this.fullEndpoint = endpoint;
    this.restTemplate = restTemplate;
  }

  public List<CrimeRecord> getData() {
    try {
      var responseEntity = this.restTemplate.getForEntity(this.fullEndpoint, CrimeRecord[].class);
      if (responseEntity.getStatusCode().is2xxSuccessful()) {
        return Arrays.asList(responseEntity.getBody());
      } else {
        throw new RuntimeException("Received non-2XX status code for endpoint " + this.getFullEndpoint());
      }
    } catch (RestClientException ex) {
      logger.error("{} request to {} failed", this.method, this.getFullEndpoint());
      throw ex;
    }
  }


}
