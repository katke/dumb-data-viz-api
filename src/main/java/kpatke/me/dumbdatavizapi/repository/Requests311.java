package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.Request311Record;
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

@Component
@Getter
public class Requests311 implements ApiRequest {
  private static final Logger logger = LoggerFactory.getLogger(Requests311.class);

  private @Value("${data.endpoints.311requests}") String baseEndpoint;
  private final HttpMethod method = HttpMethod.GET;

  public String getFullEndpoint() {
    var fullQueryString = String.format("$where=sr_short_code in('%s','%s')",
        ShortCodeType.DIBS_REMOVAL.code,
        ShortCodeType.CABLE_TV_OUTAGE.code);
    return String.format("%s?%s", baseEndpoint, fullQueryString);
  }

  public List<Request311Record> makeRequest() {
    try {
      var restTemplate = this.generateRestTemplate();
      var responseEntity = restTemplate.getForEntity(this.getFullEndpoint(), Request311Record[].class);

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

  public RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder()
        .rootUri(this.getFullEndpoint())
        .build();
  }

  enum ShortCodeType {
    DIBS_REMOVAL("SDW"),
    CABLE_TV_OUTAGE("OCC");
    private String code;

    ShortCodeType(String codeId) {
      this.code = codeId;
    }
  }

}
