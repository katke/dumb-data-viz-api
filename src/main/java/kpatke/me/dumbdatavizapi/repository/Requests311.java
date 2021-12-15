package kpatke.me.dumbdatavizapi.repository;

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

  private static @Value("${data.endpoints.311requests}") String baseEndpoint;
  private static @Value("${data.endpoints.311requests.codes.dibs}") String dibsCode;
  private static @Value("${data.endpoints.311requests.codes.cable}") String cableCode;
  private final HttpMethod method = HttpMethod.GET;

  public String getFullEndpoint() {
    var fullQueryString = String.format("$where=sr_short_code in('%s','%s')", dibsCode, cableCode);
    return String.format("%s?%s", baseEndpoint, fullQueryString);
  }

  public void makeRequest() {
    var restTemplate = this.generateRestTemplate();
    var responseEntity = restTemplate.getForEntity(this.getFullEndpoint(), Request311Record[].class);
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
        .rootUri(this.getFullEndpoint())
        .build();
  }

}
