package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelinesDTO {

    @JsonProperty("statuses")
    private PipelineDTO[] pipelines;

}