package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StsRoleDTO {
  public static final String JSON_PROPERTY_ROLE_ARN = "role_arn";
  public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";

  @JsonProperty(JSON_PROPERTY_ROLE_ARN)
  private String roleArn;

  @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
  private String externalId;

}
