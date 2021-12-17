package kpatke.me.dumbdatavizapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
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

  @Override
  public String toString() {
    var template = "ID: %s\nDate: %s\nPrimary Type: %s\nDescription: %s\n";
    return String.format(template, this.id, this.date, this.primaryType, this.description);
  }

  public boolean isUsableRecord() {
    var includesLatAndLongCoordinates = StringUtils.hasText(this.latitude)
        && StringUtils.hasText(this.longitude);
    return includesLatAndLongCoordinates;
  }

}
