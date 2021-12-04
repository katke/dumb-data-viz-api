package kpatke.me.dumbdatavizapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request311 {

  @JsonProperty("sr_number")
  private String requestNumber;
  @JsonProperty("sr_type")
  private String requestType;
  @JsonProperty("sr_short_code")
  private String requestTypeCode;
  @JsonProperty("created_date")
  private String createdDate;
  @JsonProperty("street_address")
  private String streetAddress;
  @JsonProperty("zip_code")
  private String zipcode;
  @JsonProperty("street_direction")
  private String streetDirection;
  @JsonProperty("street_number")
  private String streetNumber;
  @JsonProperty("street_name")
  private String streetName;
  @JsonProperty("street_type")
  private String streetType;
  private String latitude;
  private String longitude;

}
