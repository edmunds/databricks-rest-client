package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateProgressDetailsDTO {

    public static final String JSON_PROPERTY_STATE = "state";

    @JsonProperty(JSON_PROPERTY_STATE)
    private String state;

}
