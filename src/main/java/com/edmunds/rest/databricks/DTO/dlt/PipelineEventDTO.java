package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelineEventDTO {
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ORIGIN = "origin";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_TIMESTAMP = "timestamp";
    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_LEVEL = "level";
    public static final String JSON_PROPERTY_EVENT_TYPE = "event_type";

    @JsonProperty(JSON_PROPERTY_ID)
    private String id;

    @JsonProperty(JSON_PROPERTY_ORIGIN)
    private OriginDTO origin;

    @JsonProperty(JSON_PROPERTY_DETAILS)
    private DetailsDTO details;

    @JsonProperty(JSON_PROPERTY_TIMESTAMP)
    private String timestamp;

    @JsonProperty(JSON_PROPERTY_MESSAGE)
    private String message;

    @JsonProperty(JSON_PROPERTY_LEVEL)
    private String level;

    @JsonProperty(JSON_PROPERTY_EVENT_TYPE)
    private String eventType;

}