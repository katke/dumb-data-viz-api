package kpatke.me.dumbdatavizapi.repository;

import kpatke.me.dumbdatavizapi.model.Request311Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static kpatke.me.dumbdatavizapi.repository.Requests311.ShortCodeType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Profile("test")
public class Requests311Test {
  private static final String endpoint = "www.endpoint.com";
  private static final String test = "test";

  @Test
  @DisplayName("Happy path test that it maps 311 request types correctly into query string")
  void getFullEndpointTest() {
    var requests311 = new Requests311(
        endpoint,
        List.of(SIDEWALK_CAFE_COMPLAINT, COYOTE_INTERACTION, BEE_WASP_REMOVAL),
        test,
        test);
    var actual = requests311.getFullEndpoint();
    var expected = "www.endpoint.com?$where=sr_short_code in('CAFE','CIAC','SGG')";
    assertEquals(expected, actual);
  }

  @Test
  void getFullEndpointSingleCategory() {
    var requests311 = new Requests311(
        endpoint,
        List.of(COYOTE_INTERACTION),
        test,
        test);
    var actual = requests311.getFullEndpoint();
    var expected = "www.endpoint.com?$where=sr_short_code in('CIAC')";
    assertEquals(expected, actual);
  }

  @Test
  void getFullEndpointNoCategories() {
    var requests311 = new Requests311(
        endpoint,
        List.of(),
        test,
        test);
    assertThrows(IllegalArgumentException.class, requests311::getFullEndpoint);
  }

  @Test
  void mockTest() {
    var responseEntity = Mockito.mock(ResponseEntity.class);
    when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
    var record311 = new Request311Record();
    record311.setRequestType("DIBS_REMOVAL");
    record311.setCreatedDate("2021-03-05T11:32:29.000");
    when(responseEntity.getBody()).thenReturn(new Request311Record[]{record311});
    var requests311 = new Requests311(endpoint, List.of(DIBS_REMOVAL), test, test);
    var requests311Spy = Mockito.spy(requests311);
    when(requests311Spy.getFullEndpoint()).thenReturn(endpoint);
    var restTemplate = new RestTemplateBuilder().rootUri(endpoint).build();
    var restTemplateSpy = spy(restTemplate);
    when(restTemplateSpy.getForEntity(endpoint, Request311Record[].class)).thenReturn(responseEntity);
    when(requests311Spy.generateRestTemplate()).thenReturn(restTemplateSpy);
    when(requests311Spy.generateRestTemplate()).thenReturn(restTemplateSpy);
    var actual = requests311.getData();
    assertEquals(record311, actual.get(0));
    assertEquals(1, actual.size());
  // TODO inject rest template as a param
  }

}
