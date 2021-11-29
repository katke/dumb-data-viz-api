package kpatke.me.dumbdatavizapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties
public class CrimeRecord {
  private String id;
  private String date;
  @JsonProperty("primary_type")
  private String primaryType;
  private String description;
  @JsonProperty("location_description")
  private String locationDescription;
  @JsonProperty("updated_on")
  private String updatedDate;
  private String latitude;
  private String longitude;

}
