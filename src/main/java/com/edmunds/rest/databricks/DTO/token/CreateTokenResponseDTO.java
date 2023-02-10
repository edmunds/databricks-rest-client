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
public class CreateTokenResponseDTO<T extends TokenInfoDTO> {

  private static final String JSON_PROPERTY_TOKEN_VALUE = "token_value";
  private static final String JSON_PROPERTY_TOKEN_INFO = "token_info";

  @JsonProperty(JSON_PROPERTY_TOKEN_VALUE)
  private String tokenValue;

  @JsonProperty(JSON_PROPERTY_TOKEN_INFO)
  private T tokenInfo;
}
