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
import com.edmunds.rest.databricks.DTO.ClusterEventDTO;
import com.edmunds.rest.databricks.DTO.ClusterEventTypeDTO;
import com.edmunds.rest.databricks.DTO.ClusterEventsDTO;
import com.edmunds.rest.databricks.DTO.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.NewClusterDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.request.EditClusterRequest;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * A basic implementation of the ClusterService.
 */
public final class ClusterServiceImpl extends DatabricksService implements ClusterService {

  private static Logger log = Logger.getLogger(ClusterServiceImpl.class);

  public ClusterServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Deprecated
  @Override
  public String create(CreateClusterRequest createClusterRequest)
      throws IOException, DatabricksRestException {
    byte[] responseBody = client
        .performQuery(RequestMethod.POST, "/clusters/create", createClusterRequest.getData());
    Map<String, Object> response = this.mapper.readValue(responseBody, Map.class);
    return (String) response.get("cluster_id");
  }

  @Override
  public String create(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException {
    String marshalled = this.mapper.writeValueAsString(clusterDTO);
    Map<String, Object> data = this.mapper
        .readValue(marshalled, new TypeReference<Map<String, Object>>() {
        });
    byte[] responseBody = client.performQuery(RequestMethod.POST, "/clusters/create", data);
    Map<String, String> response = this.mapper.readValue(
            responseBody, new TypeReference<Map<String, Long>>() {});
    return response.get("cluster_id");
  }

  @Deprecated
  @Override
  public void edit(EditClusterRequest editClusterRequest)
      throws IOException, DatabricksRestException {
    client.performQuery(RequestMethod.POST, "/clusters/edit", editClusterRequest.getData());
  }

  @Override
  public void edit(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException {
    String marshalled = this.mapper.writeValueAsString(clusterDTO);
    Map<String, Object> data = this.mapper
        .readValue(marshalled, new TypeReference<Map<String, Object>>() {
        });
    client.performQuery(RequestMethod.POST, "/clusters/edit", data);
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

  @Override
  public List<ClusterEventDTO> listEvents(String clusterId, ClusterEventTypeDTO[] eventsToFilter,
      int offset, int limit) throws
      IOException,
      DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    data.put("event_types", eventsToFilter);
    data.put("offset", offset);
    data.put("limit", limit);
    byte[] responseBody = client.performQuery(RequestMethod.POST, "/clusters/events", data);
    ClusterEventsDTO clusterEvents = this.mapper.readValue(responseBody, ClusterEventsDTO.class);
    return clusterEvents.getEvents();
  }

  @Override
  public void upsertCluster(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException {
    String clusterName = clusterDTO.getClusterName();
    boolean clusterExists = false;
    String clusterId = "";
    ClusterInfoDTO[] clusters = list();
    for (ClusterInfoDTO cluster : clusters) {
      if (clusterName.equals(cluster.getClusterName())) {
        clusterExists = true;
        clusterId = cluster.getClusterId();
        break;
      }
    }

    if (clusterExists) {
      edit(clusterDTO);
      log.info(String.format("Updated cluster, cluster_id: %s", clusterId));
    } else {
      clusterId = create(clusterDTO);
      log.info(String.format("Created cluster, cluster_id: %s", clusterId));
    }
  }

}
