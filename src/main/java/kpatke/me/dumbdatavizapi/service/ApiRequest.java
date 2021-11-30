package kpatke.me.dumbdatavizapi.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public interface ApiRequest {
  String getEndpoint();
  HttpMethod getMethod();
  void makeRequest();

  // TODO: Apparently RestTemplate to be deprecated, update to WebClient in future
  default RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder().build();
  }

}
