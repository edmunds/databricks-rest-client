package com.edmunds.rest.databricks.DTO.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class JobClusterDTO implements Serializable {

    @JsonProperty("job_cluster_key")
    private String jobClusterKey;
    @JsonProperty("new_cluster")
    private NewClusterDTO newCluster;
}
