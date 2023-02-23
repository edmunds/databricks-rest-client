package com.edmunds.rest.databricks.DTO.sqlWarehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SqlWarehouseDTO {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CLUSTER_SIZE = "cluster_size";

    @JsonProperty(JSON_PROPERTY_ID)
    private String sqlEndpointId;
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;
    @JsonProperty(JSON_PROPERTY_CLUSTER_SIZE)
    private String clusterSize;
}
