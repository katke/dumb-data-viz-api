package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.Request311Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class Requests311 implements ApiRequest {
  private static final Logger logger = LoggerFactory.getLogger(Requests311.class);

  private final String baseEndpoint;
  private final HttpMethod method = HttpMethod.GET;
  private final List<ShortCodeType> shortTypeFilters;

  public Requests311(String baseEndpoint, List<ShortCodeType> filterByTypes) {
    this.shortTypeFilters = filterByTypes;
    this.baseEndpoint = baseEndpoint;
  }

  public String getFullEndpoint() {
    var filterTemplate = "'%s',";
    var strBuilder = new StringBuilder().append("$where=sr_short_code in(");
    shortTypeFilters.stream().forEach(shortCode -> strBuilder.append(String.format(filterTemplate, shortCode.code)));
    strBuilder.deleteCharAt(strBuilder.length() - 1)
        .append(")");
    var fullQueryString = strBuilder.toString();
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

  public enum ShortCodeType {
    DIBS_REMOVAL("SDW"),
    CABLE_TV_OUTAGE("OCC"),
    ICE_SNOW_REMOVAL("SDO"),
    SIDEWALK_CAFE_COMPLAINT("CAFE"),
    BEE_WASP_REMOVAL("SGG"),
    COYOTE_INTERACTION("CIAC"),
    UNCLEARED_SNOW_SIDEWALK("SWSNOREM");
    private String code;

    ShortCodeType(String codeId) {
      this.code = codeId;
    }
  }

}
