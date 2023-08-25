package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.clusters.ClusterPoliciesDTO;
import com.edmunds.rest.databricks.DTO.clusters.ClusterPolicyDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class ClusterPoliciesServiceImpl extends DatabricksService implements ClusterPoliciesService {

  /**
   * Given a client, create a wrapper around it.
   */
  public ClusterPoliciesServiceImpl(DatabricksRestClient client) {
    super(client);
  }

  @Override
  public ClusterPolicyDTO[] list() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/policies/clusters/list", null);
    ClusterPoliciesDTO policies = this.mapper.readValue(responseBody, ClusterPoliciesDTO.class);
    return policies.getPolicies();
  }
}
