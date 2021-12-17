package kpatke.me.dumbdatavizapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request311Record {

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

  @Override
  public String toString() {
    var template = "Req Num: %s\nCreated: %s\nReq Type: %s\nReq Street Address: %s";
    var address = this.streetAddress;
    if (!StringUtils.hasText(address)) {
      address = "Address not included";
    }
    return String.format(template, this.requestNumber, this.createdDate, this.requestType, address);
  }

  public boolean isUsableRecord() {
    var includesLatAndLongCoordinates = StringUtils.hasText(this.latitude)
        && StringUtils.hasText(this.longitude);
    var includesAddressInfo = StringUtils.hasText(this.streetAddress);
    return includesLatAndLongCoordinates || includesAddressInfo;
  }

}
