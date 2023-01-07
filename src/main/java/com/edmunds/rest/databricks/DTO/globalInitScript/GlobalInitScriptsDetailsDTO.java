package com.edmunds.rest.databricks.DTO.globalInitScript;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalInitScriptsDetailsDTO {

    @JsonProperty("scripts")
    private GlobalInitScriptDetailsDTO[] scripts;

}
