package com.edmunds.rest.databricks.DTO.instance_profiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 */
@Data
public class InstanceProfileDTO {

  @JsonProperty("instance_profile_arn")
  private String instanceProfileArn;

}
