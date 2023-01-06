package com.edmunds.rest.databricks.DTO.globalInitScript;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalInitScriptDetailsDTO {

    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("name")
    private String name;
    @JsonProperty("position")
    private int position;
    @JsonProperty("script_id")
    private String scriptId;
    @JsonProperty("updated_at")
    private Long updatedAt;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("script")
    private String script;
}
