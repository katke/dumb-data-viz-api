package kpatke.me.dumbdatavizapi.service;

import kpatke.me.dumbdatavizapi.model.Request311Record;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Component
@Getter
public class Requests311 implements ApiRequest {
  private static final Logger logger = LoggerFactory.getLogger(Requests311.class);

  private @Value("${data.endpoints.311requests}") String endpoint;
  private final HttpMethod method = HttpMethod.GET;
  private final String dibsRemovalQueryParam = "sr_short_code=SDW";

  // TODO improve design to better support multiple query strings on the same API
  public String getEndpoint() {
    return String.format("%s?%s", this.endpoint, dibsRemovalQueryParam);
  }

  public void makeRequest() {
    var restTemplate = this.generateRestTemplate();
    var fullEndpoint = this.getEndpoint();
    var responseEntity = restTemplate.getForEntity(fullEndpoint, Request311Record[].class);
    logger.info(responseEntity.getStatusCode().toString());
    if (responseEntity.getBody() != null) {
      var arbitraryFirstRecord = Arrays.stream(responseEntity.getBody()).findFirst();
      arbitraryFirstRecord.ifPresent(request311Record -> logger.info(request311Record.toString()));
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
