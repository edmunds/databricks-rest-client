package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelineEventsDTO {
    public static final  String JSON_PROPERTY_EVENTS_JSON = "events_json";
    public static final  String JSON_PROPERTY_EVENTS = "events";
    public static final  String JSON_PROPERTY_NEXT_PAGE_TOKEN = "next_page_token";
    public static final  String JSON_PROPERTY_PREV_PAGE_TOKEN = "prev_page_token";

    @JsonProperty(JSON_PROPERTY_EVENTS_JSON)
    private String[] eventsJson;

    @JsonProperty(JSON_PROPERTY_EVENTS)
    private PipelineEventDTO[] events;

    @JsonProperty(JSON_PROPERTY_NEXT_PAGE_TOKEN)
    private String nextPageToken;

    @JsonProperty(JSON_PROPERTY_PREV_PAGE_TOKEN)
    private String prevPageToken;

}