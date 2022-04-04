package com.edmunds.rest.databricks.DTO;

import com.edmunds.rest.databricks.DTO.clusters.ClusterInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 *
 */
@Data
public class UpsertClusterDTO extends ClusterInfoDTO implements Serializable {

  @JsonProperty("cluster_id")
  private String clusterId;

}
