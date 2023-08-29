package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.unity.CurrentMetastoreAssignmentDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;

import java.io.IOException;
import java.util.HashMap;

public class UnityCatalogWorkspaceServiceImpl extends DatabricksService implements UnityCatalogWorkspaceService {


  public UnityCatalogWorkspaceServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public CurrentMetastoreAssignmentDTO currentMetastoreAssignment() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/unity-catalog/current-metastore-assignment",
        new HashMap<>());
    return this.mapper.readValue(responseBody, CurrentMetastoreAssignmentDTO.class);
  }
}
