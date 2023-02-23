package com.edmunds.rest.databricks.DTO.sqlWarehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SqlWarehousesDTO {

    @JsonProperty("warehouses")
    private SqlWarehouseDTO[] sqlWarehouses;
}
