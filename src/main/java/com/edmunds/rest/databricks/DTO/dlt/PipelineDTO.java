package com.edmunds.rest.databricks.DTO.dlt;

import com.edmunds.rest.databricks.DTO.DatabricksAssetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelineDTO implements DatabricksAssetDTO {

    public static final String JSON_PROPERTY_PIPELINE_ID = "pipeline_id";
    public static final String JSON_PROPERTY_STATE = "state";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_LATEST_UPDATES = "latest_updates";
    public static final String JSON_PROPERTY_CREATOR_USER_NAME = "creator_user_name";
    public static final String JSON_PROPERTY_RUN_AS_USER_NAME = "run_as_user_name";

    @JsonProperty(JSON_PROPERTY_PIPELINE_ID)
    private String pipelineId;
    @JsonProperty(JSON_PROPERTY_STATE)
    private PipelineUpdateResultStateDTO state;
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;
    @JsonProperty(JSON_PROPERTY_LATEST_UPDATES)
    private LatestUpdateDTO[] latestUpdates;
    @JsonProperty(JSON_PROPERTY_CREATOR_USER_NAME)
    private String creatorUsername;
    @JsonProperty(JSON_PROPERTY_RUN_AS_USER_NAME)
    private String runAsUsername;

    @Override
    public String getId() {
        return pipelineId;
    }
}