package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PipelineSpecDTO {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_STORAGE = "storage";
    public static final String JSON_PROPERTY_CONFIGURATION = "configuration";
    public static final String JSON_PROPERTY_CLUSTERS = "clusters";
    public static final String JSON_PROPERTY_LIBRARIES = "libraries";
    public static final String JSON_PROPERTY_TARGET = "target";
    public static final String JSON_PROPERTY_CONTINUOUS = "continuous";
    public static final String JSON_PROPERTY_DEVELOPMENT = "development";
    public static final String JSON_PROPERTY_PHOTON = "photon";
    public static final String JSON_PROPERTY_EDITION = "edition";
    public static final String JSON_PROPERTY_CHANNEL = "channel";

    @JsonProperty(JSON_PROPERTY_ID)
    private String id;

    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    @JsonProperty(JSON_PROPERTY_STORAGE)
    private String storage;

    @JsonProperty(JSON_PROPERTY_CONFIGURATION)
    private Map<String, String> configuration;

    @JsonProperty(JSON_PROPERTY_CLUSTERS)
    private PipelineClusterDTO[] clusters;

    @JsonProperty(JSON_PROPERTY_LIBRARIES)
    private PipelineLibraryDTO[] libraries;

    @JsonProperty(JSON_PROPERTY_TARGET)
    private String target;

    @JsonProperty(JSON_PROPERTY_CONTINUOUS)
    private boolean continuous;

    @JsonProperty(JSON_PROPERTY_DEVELOPMENT)
    private boolean development;

    @JsonProperty(JSON_PROPERTY_PHOTON)
    private boolean photon;

    @JsonProperty(JSON_PROPERTY_EDITION)
    private String edition;

    @JsonProperty(JSON_PROPERTY_CHANNEL)
    private String channel;
}