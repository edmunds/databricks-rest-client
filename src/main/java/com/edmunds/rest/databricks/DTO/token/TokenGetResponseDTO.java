package com.edmunds.rest.databricks.DTO.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenGetResponseDTO<T extends TokenInfoDTO> {

  private static final String JSON_PROPERTY_TOKEN_INFOS = "token_info";

  @JsonProperty(JSON_PROPERTY_TOKEN_INFOS)
  private T tokenInfo;
}
