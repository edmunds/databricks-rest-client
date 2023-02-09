package com.edmunds.rest.databricks.DTO.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenDTO {

  private static final String JSON_PROPERTY_LIFETIME_IN_SECONDS = "lifetime_seconds";

  private String token;

  @JsonProperty(JSON_PROPERTY_LIFETIME_IN_SECONDS)
  private long lifetimeInSeconds;
}
