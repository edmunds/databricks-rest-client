package com.edmunds.rest.databricks.DTO.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RunJobTask {
    public static final String JSON_PROPERTY_JOB_ID = "job_id";

    @JsonProperty(JSON_PROPERTY_JOB_ID)
    private long jobId;

}
