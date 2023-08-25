package com.edmunds.rest.databricks.DTO.clusters;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClusterPoliciesDTO {

  @JsonProperty("policies")
  private ClusterPolicyDTO[] policies;

  @JsonProperty("total_count")
  private int totalCount;
}
