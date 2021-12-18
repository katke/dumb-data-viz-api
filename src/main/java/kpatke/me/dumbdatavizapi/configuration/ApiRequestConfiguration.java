package kpatke.me.dumbdatavizapi.configuration;


import kpatke.me.dumbdatavizapi.repository.Requests311;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static kpatke.me.dumbdatavizapi.repository.Requests311.ShortCodeType.*;

@Configuration
public class ApiRequestConfiguration {

  @Bean
  public Requests311 lineGraph311Request(
      @Value("${data.endpoints.311requests}") String baseEndpoint311,
      @Value("${CHI_OPEN_DATA_USER}") String username,
      @Value("${CHI_OPEN_DATA_SECRET}") String secret) {
    var requestTypes = List.of(
        DIBS_REMOVAL,
        SIDEWALK_CAFE_COMPLAINT,
        UNCLEARED_SNOW_SIDEWALK,
        ICE_SNOW_REMOVAL,
        BEE_WASP_REMOVAL
    );
    return new Requests311(baseEndpoint311, requestTypes, username, secret);
  }

}
