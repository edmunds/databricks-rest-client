package com.edmunds.rest.databricks.DTO.globalInitScript;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalInitScriptDTO {

    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("name")
    private String name;
    @JsonProperty("position")
    private int position;
    @JsonProperty("script")
    private String script;

}
