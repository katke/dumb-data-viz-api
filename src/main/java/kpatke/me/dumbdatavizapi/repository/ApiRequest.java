package kpatke.me.dumbdatavizapi.repository;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import java.util.List;

// TODO use CrudRepository once switched to datastore
public interface ApiRequest {
  String getFullEndpoint();

  List<?> getData();

  // TODO: Apparently RestTemplate to be deprecated, update to WebClient in future
  default RestTemplate generateRestTemplate() {
    return new RestTemplateBuilder().build();
  }

  // TODO flesh out this common behavior
  /* default List<?> makeRequest(URIBuilder, Class<T>) {
   try {
      var restTemplate = this.generateRestTemplate();
      var responseEntity = restTemplate.getForEntity(this.fullEndpoint, CrimeRecord[].class);
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
   */


}
