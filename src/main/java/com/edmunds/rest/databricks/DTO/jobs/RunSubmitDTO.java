package com.edmunds.rest.databricks.DTO.jobs;

import com.edmunds.rest.databricks.DTO.perimissions.AccessControlRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class RunSubmitDTO implements Serializable {
    @JsonProperty("tasks")
    private RunSubmitTaskDTO[] tasks;
    @JsonProperty("run_name")
    private String runName;
    @JsonProperty("timeout_seconds")
    private int timeoutSeconds;
    @JsonProperty("idempotency_token")
    private String idempotencyToken;
    @JsonProperty("access_control_list")
    private AccessControlRequestDTO[] accessControlRequests;
}
