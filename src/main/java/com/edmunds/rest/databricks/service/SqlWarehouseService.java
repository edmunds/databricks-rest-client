package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.sqlWarehouse.SqlWarehousesDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

public interface SqlWarehouseService {

  SqlWarehousesDTO listSqlWarehouses() throws IOException, DatabricksRestException;

}
