package com.edmunds.rest.databricks.DTO.clusters;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClusterPolicyDTO {

  @JsonProperty("policy_id")
  private String policyId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("definition")
  private String definition;

  @JsonProperty("description")
  private String description;

  @JsonProperty("policy_family_id")
  private String policyFamilyId;

  @JsonProperty("policy_family_version")
  private Integer policyFamilyVersion;

  @JsonProperty("created_at_timestamp")
  private long created_at_timestamp;

  @JsonProperty("is_default")
  private boolean isDefault;


}
