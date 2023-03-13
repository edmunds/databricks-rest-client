package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.instance_pools.InstancePoolsDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;

import java.io.IOException;
import java.util.HashMap;

public class PoolServiceImpl extends DatabricksService implements PoolService {

  public PoolServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public InstancePoolsDTO list() throws DatabricksRestException, IOException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/instance-pools/list", new HashMap<>());
    return this.mapper.readValue(responseBody, InstancePoolsDTO.class);
  }

}
