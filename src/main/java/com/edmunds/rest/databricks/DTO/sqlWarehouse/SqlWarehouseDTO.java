package com.edmunds.rest.databricks.DTO.sqlWarehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SqlWarehouseDTO {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CLUSTER_SIZE = "cluster_size";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_AUTO_STOP_MINS = "auto_stop_mins";
    public static final String JSON_PROPERTY_CREATOR_NAME = "creator_name";
    public static final String JSON_PROPERTY_STATE = "state";
    public static final String JSON_PROPERTY_WAREHOUSE_TYPE = "warehouse_type";
    public static final String JSON_PROPERTY_ENABLE_SERVERLESS_COMPUTE = "enable_serverless_compute";

    @JsonProperty(JSON_PROPERTY_ID)
    private String sqlEndpointId;
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;
    @JsonProperty(JSON_PROPERTY_CLUSTER_SIZE)
    private String clusterSize;
    @JsonProperty(JSON_PROPERTY_SIZE)
    private String size;
    @JsonProperty(JSON_PROPERTY_AUTO_STOP_MINS)
    private Integer autoStopMins;
    @JsonProperty(JSON_PROPERTY_CREATOR_NAME)
    private String creatorName;
    @JsonProperty(JSON_PROPERTY_STATE)
    private String state;
    @JsonProperty(JSON_PROPERTY_WAREHOUSE_TYPE)
    private String warehouseType;
    @JsonProperty(JSON_PROPERTY_ENABLE_SERVERLESS_COMPUTE)
    private Boolean enableServerlessCompute;
}
