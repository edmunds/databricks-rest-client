package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LatestUpdateDTO {
    public static final String JSON_PROPERTY_UPDATE_ID = "update_id";
    public static final String JSON_PROPERTY_STATE = "state";
    public static final String JSON_PROPERTY_CREATION_TIME = "creation_time";

    @JsonProperty(JSON_PROPERTY_UPDATE_ID)
    private String updateId;

    @JsonProperty(JSON_PROPERTY_STATE)
    private String state;

    @JsonProperty(JSON_PROPERTY_CREATION_TIME)
    private PipelineUpdateResultStateDTO creationTime;

}