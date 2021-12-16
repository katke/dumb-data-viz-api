package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.CrimeRecord;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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

  public List<CrimeRecord> makeRequest() {
    try {
      var restTemplate = this.generateRestTemplate();
      var responseEntity = restTemplate.getForEntity(this.getFullEndpoint(), CrimeRecord[].class);
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
