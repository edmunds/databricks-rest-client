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
public class TokenInfoDTO {

  private static final String JSON_PROPERTY_TOKEN_ID = "token_id";
  private static final String JSON_PROPERTY_CREATION_TIME = "creation_time";
  private static final String JSON_PROPERTY_EXPIRY_TIME = "expiry_time";

  @JsonProperty(JSON_PROPERTY_TOKEN_ID)
  protected String tokenId;

  @JsonProperty(JSON_PROPERTY_CREATION_TIME)
  protected long creationTime;

  @JsonProperty(JSON_PROPERTY_EXPIRY_TIME)
  protected long expiryTime;

  protected String comment;
}
