package com.edmunds.rest.databricks.DTO.clusters;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class DockerImageDTO {
    @JsonProperty("url")
    private String url;
    @JsonProperty("basic_auth")
    private Map<String, String> basicAuth;
}
