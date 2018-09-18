/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.AutoScaleDTO;
import com.edmunds.rest.databricks.DTO.ClusterInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestClient;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.request.EditClusterRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of the ClusterService.
 */
public final class ClusterServiceImpl extends DatabricksService implements ClusterService {

  public ClusterServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public String create(CreateClusterRequest createClusterRequest)
      throws IOException, DatabricksRestException {
    byte[] responseBody = client
        .performQuery(RequestMethod.POST, "/clusters/create", createClusterRequest.getData());
    Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);
    return (String) response.get("cluster_id");
  }

  @Override
  public void edit(EditClusterRequest editClusterRequest)
      throws IOException, DatabricksRestException {
    client.performQuery(RequestMethod.POST, "/clusters/edit", editClusterRequest.getData());
  }

  @Override
  public void start(String clusterId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    client.performQuery(RequestMethod.POST, "/clusters/start", data);
  }

  @Override
  public void restart(String clusterId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    client.performQuery(RequestMethod.POST, "/clusters/restart", data);
  }

  @Override
  public void resize(int numWorkers, String clusterId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("num_workers", numWorkers);
    data.put("cluster_id", clusterId);
    client.performQuery(RequestMethod.POST, "/clusters/resize", data);
  }

  @Override
  public void resize(AutoScaleDTO autoscale, String clusterId)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("autoscale", autoscale);
    data.put("cluster_id", clusterId);
    client.performQuery(RequestMethod.POST, "/clusters/resize", data);
  }

  @Override
  public void delete(String clusterId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    client.performQuery(RequestMethod.POST, "/clusters/delete", data);
  }

  @Override
  public ClusterInfoDTO getInfo(String clusterId) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/clusters/get", data);
    return this.mapper.readValue(responseBody, ClusterInfoDTO.class);
  }

  @Override
  public ClusterInfoDTO[] list() throws IOException, DatabricksRestException {
    byte[] responseBody = client.performQuery(RequestMethod.GET, "/clusters/list", null);
    Map<String, ClusterInfoDTO[]> jsonObject = this.mapper
        .readValue(responseBody, new TypeReference<Map<String, ClusterInfoDTO[]>>() {
        });
    return jsonObject.get("clusters");
  }
}
