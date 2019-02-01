package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Data
public class InstanceProfileDTO {
  @Getter @Setter @JsonProperty("instance_profile_arn")
  private String instanceProfileArn;
}
