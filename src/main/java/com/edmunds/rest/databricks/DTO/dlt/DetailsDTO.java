package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DetailsDTO {

    public static final String JSON_PROPERTY_UPDATE_PROGRESS = "update_progress";

    @JsonProperty(JSON_PROPERTY_UPDATE_PROGRESS)
    private UpdateProgressDetailsDTO updateProgress;

}
