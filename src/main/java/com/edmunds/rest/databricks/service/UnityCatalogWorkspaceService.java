package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.unity.CurrentMetastoreAssignmentDTO;
import com.edmunds.rest.databricks.DTO.unity.MetastoreDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;

/**
 * Wrapper around the Workspace API.
 * @see <a href="https://docs.databricks.com/api/workspace/metastores">https://docs.databricks.com/api/workspace/metastores</a>
 */
public interface UnityCatalogWorkspaceService {

  /**
   * Return current metastore assignment.
   *
   * @return current metastore assignment for a workspace
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/workspace/metastores/current">https://docs.databricks.com/api/workspace/metastores/current</a>
   */
  CurrentMetastoreAssignmentDTO currentMetastoreAssignment() throws IOException, DatabricksRestException;

}
