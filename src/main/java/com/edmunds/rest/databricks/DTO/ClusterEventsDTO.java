package com.edmunds.rest.databricks.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ClusterEventsDTO {
    @JsonProperty("events")
    private List<ClusterEventDTO> events;

    public List<ClusterEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<ClusterEventDTO> events) {
        this.events = events;
    }
}
