package com.edmunds.rest.databricks.DTO.token.management;

import com.edmunds.rest.databricks.DTO.token.TokenInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenManagementInfoDTO extends TokenInfoDTO {

  private static final String JSON_PROPERTY_CREATED_BY_ID = "created_by_id";
  private static final String JSON_PROPERTY_CREATED_BY_USERNAME = "created_by_username";
  private static final String JSON_PROPERTY_OWNER_ID = "owner_id";

  @JsonProperty(JSON_PROPERTY_CREATED_BY_ID)
  private long createdById;

  @JsonProperty(JSON_PROPERTY_CREATED_BY_USERNAME)
  private String createdByUsername;

  @JsonProperty(JSON_PROPERTY_OWNER_ID)
  private long ownerId;

}
