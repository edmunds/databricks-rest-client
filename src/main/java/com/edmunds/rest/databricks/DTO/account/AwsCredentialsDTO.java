package com.edmunds.rest.databricks.DTO.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AwsCredentialsDTO {
  public static final String JSON_PROPERTY_STS_ROLE = "sts_role";

  @JsonProperty(JSON_PROPERTY_STS_ROLE)
  private StsRoleDTO stsRole;

}
