package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PipelineLibraryDTO {

    public static final String JSON_PROPERTY_NOTEBOOK = "notebook";

    @JsonProperty(JSON_PROPERTY_NOTEBOOK)
    private Map<String, String> notebook;

}