package com.edmunds.rest.databricks.DTO.token.management;

import com.edmunds.rest.databricks.DTO.token.CreateTokenDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOnBehalfOfTokenDTO extends CreateTokenDTO {

  public static final String JSON_PROPERTY_APPLICATION_ID = "application_id";

  @JsonProperty(JSON_PROPERTY_APPLICATION_ID)
  private String applicationId;

  public CreateOnBehalfOfTokenDTO(String comment, long lifetimeInSeconds, String applicationId) {
    super(comment, lifetimeInSeconds);
    this.applicationId = applicationId;
  }
}
