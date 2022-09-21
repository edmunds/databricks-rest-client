package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OriginDTO {
    public static final String JSON_PROPERTY_CLOUD = "cloud";
    public static final String JSON_PROPERTY_REGION = "region";
    public static final String JSON_PROPERTY_ORG_ID = "org_id";
    public static final String JSON_PROPERTY_PIPELINE_ID = "pipeline_id";
    public static final String JSON_PROPERTY_PIPELINE_NAME = "pipeline_name";
    public static final String JSON_PROPERTY_CLUSTER_ID = "cluster_id";
    public static final String JSON_PROPERTY_UPDATE_ID = "update_id";

    @JsonProperty(JSON_PROPERTY_CLOUD)
    private String cloud;
    @JsonProperty(JSON_PROPERTY_REGION)
    private String region;
    @JsonProperty(JSON_PROPERTY_ORG_ID)
    private Long orgId;
    @JsonProperty(JSON_PROPERTY_PIPELINE_ID)
    private String pipelineId;
    @JsonProperty(JSON_PROPERTY_PIPELINE_NAME)
    private String pipelineName;
    @JsonProperty(JSON_PROPERTY_CLUSTER_ID)
    private String clusterId;
    @JsonProperty(JSON_PROPERTY_UPDATE_ID)
    private String updateId;

}