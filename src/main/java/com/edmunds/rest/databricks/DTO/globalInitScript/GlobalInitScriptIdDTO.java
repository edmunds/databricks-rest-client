package com.edmunds.rest.databricks.DTO.globalInitScript;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalInitScriptIdDTO {

    @JsonProperty("script_id")
    private String scriptId;

}
