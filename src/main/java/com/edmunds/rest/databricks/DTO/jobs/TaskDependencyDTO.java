package com.edmunds.rest.databricks.DTO.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class TaskDependencyDTO implements Serializable {
    @JsonProperty("task_key")
    private String taskKey;
}
