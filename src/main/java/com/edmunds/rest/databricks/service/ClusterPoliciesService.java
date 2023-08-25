package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.clusters.ClusterPolicyDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;

/**
 * A Wrapper around the cluster part of the databricks rest api.
 *
 * @see <a href="https://docs.databricks.com/api/workspace/clusterpolicies">https://docs.databricks.com/api/workspace/clusterpolicies</a>
 */
public interface ClusterPoliciesService {

  /**
   * Lists all the clusters policies on a given databricks instance.
   *
   * @return an array of cluster policies information objects
   * @throws IOException             any other errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://docs.databricks.com/api/workspace/clusterpolicies/list">https://docs.databricks.com/api/workspace/clusterpolicies/list</a>
   */
  ClusterPolicyDTO[] list() throws IOException, DatabricksRestException;
}
