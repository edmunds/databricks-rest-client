package com.edmunds.rest.databricks.DTO.dlt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PipelineClusterDTO {
    public static final String JSON_PROPERTY_LABEL = "label";
    public static final String JSON_PROPERTY_NODE_TYPE_ID = "node_type_id";
    public static final String JSON_PROPERTY_DRIVER_NODE_TYPE_ID = "driver_node_type_id";
    public static final String JSON_PROPERTY_AUTOSCADE = "autoscale";

    @JsonProperty(JSON_PROPERTY_LABEL)
    private String label;

    @JsonProperty(JSON_PROPERTY_NODE_TYPE_ID)
    private String nodeTypeId;

    @JsonProperty(JSON_PROPERTY_DRIVER_NODE_TYPE_ID)
    private String driverNodeTypeId;

    @JsonProperty(JSON_PROPERTY_AUTOSCADE)
    private Map<String, String> autoscale;

}
