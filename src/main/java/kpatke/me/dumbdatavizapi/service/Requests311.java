package kpatke.me.dumbdatavizapi.service;

import kpatke.me.dumbdatavizapi.model.CrimeRecord;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Getter
public class Requests311 implements ApiRequest {
  private static final Logger logger = LoggerFactory.getLogger(Requests311.class);

  private @Value("${data.endpoints.311requests}") String endpoint;
  private final HttpMethod method = HttpMethod.GET;
  private final String dibsRemovalQueryParam = "?sr_short_code=SDW";

  // TODO improve design to better support multiple query strings on the same API
  public String getEndpoint() {
    return String.format("%s?%s", this.endpoint, dibsRemovalQueryParam);
  }

  public void makeRequest() {
    var restTemplate = this.generateRestTemplate();
    var responseEntity = restTemplate.getForEntity(this.getEndpoint(), CrimeRecord[].class);
    logger.info(responseEntity.getStatusCode().toString());
    if (responseEntity.getBody() != null) {
      var arbitraryFirstRecord = Arrays.stream(responseEntity.getBody()).findFirst();
      arbitraryFirstRecord.ifPresent(crimeRecord -> logger.info(crimeRecord.toString()));
    } else {
      logger.info("Response body is null");
    }
  }

  public RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder()
        .rootUri(this.getEndpoint())
        .build();
  }

}
