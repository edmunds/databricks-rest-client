package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Data
public class UpsertClusterDTO extends NewClusterDTO implements Serializable {
  @Getter @Setter @JsonProperty("cluster_id") private String clusterId;
}
