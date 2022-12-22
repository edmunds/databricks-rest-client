package com.edmunds.rest.databricks.DTO.jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SqlTask {
    public static final String JSON_PROPERTY_WAREHOUSE_ID = "warehouse_id";

    @JsonProperty(JSON_PROPERTY_WAREHOUSE_ID)
    private String warehouseId;

}
