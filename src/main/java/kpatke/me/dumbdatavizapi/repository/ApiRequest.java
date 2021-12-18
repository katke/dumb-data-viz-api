package kpatke.me.dumbdatavizapi.repository;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

// TODO use CrudRepository once switched to datastore
public interface ApiRequest {
  String getFullEndpoint();
  List<?> makeRequest();

  // TODO: Apparently RestTemplate to be deprecated, update to WebClient in future
  default RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder().build();
  }

}
