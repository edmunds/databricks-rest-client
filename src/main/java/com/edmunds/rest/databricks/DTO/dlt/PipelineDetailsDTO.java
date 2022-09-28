package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelineDetailsDTO {

    public static final String JSON_PROPERTY_PIPELINE_ID = "pipeline_id";
    public static final String JSON_PROPERTY_SPEC = "spec";
    public static final String JSON_PROPERTY_STATE = "state";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_LATEST_UPDATES = "latest_updates";
    public static final String JSON_PROPERTY_LAST_MODIFIED = "last_modified";
    public static final String JSON_PROPERTY_CREATOR_USER_NAME = "creator_user_name";
    public static final String JSON_PROPERTY_RUN_AS_USER_NAME = "run_as_user_name";

    @JsonProperty(JSON_PROPERTY_PIPELINE_ID)
    private String pipelineId;

    @JsonProperty(JSON_PROPERTY_SPEC)
    private PipelineSpecDTO spec;

    @JsonProperty(JSON_PROPERTY_STATE)
    private PipelineUpdateResultStateDTO state;

    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    @JsonProperty(JSON_PROPERTY_LAST_MODIFIED)
    private Long lastModified;

    @JsonProperty(JSON_PROPERTY_CREATOR_USER_NAME)
    private String creatorUserName;

    @JsonProperty(JSON_PROPERTY_RUN_AS_USER_NAME)
    private String runAsUserName;

}