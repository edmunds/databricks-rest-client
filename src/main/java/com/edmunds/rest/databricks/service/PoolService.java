package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.instance_pools.InstancePoolsDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

public interface PoolService {

  InstancePoolsDTO list() throws DatabricksRestException, IOException;

}
