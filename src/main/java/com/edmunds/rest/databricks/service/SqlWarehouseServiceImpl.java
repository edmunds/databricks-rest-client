package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.sqlWarehouse.SqlWarehousesDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;

import java.io.IOException;
import java.util.HashMap;

public class SqlWarehouseServiceImpl extends DatabricksService implements SqlWarehouseService {

  public SqlWarehouseServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public SqlWarehousesDTO listSqlWarehouses() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/sql/warehouses", new HashMap<>());
    return this.mapper.readValue(responseBody, SqlWarehousesDTO.class);
  }

}
