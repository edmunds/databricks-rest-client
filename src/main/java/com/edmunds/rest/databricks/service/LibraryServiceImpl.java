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

import com.edmunds.rest.databricks.DTO.ClusterLibraryStatusesDTO;
import com.edmunds.rest.databricks.DTO.LibraryDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of LibraryService.
 */
public final class LibraryServiceImpl extends DatabricksService implements LibraryService {

  public LibraryServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public ClusterLibraryStatusesDTO[] allClusterStatuses()
      throws IOException, DatabricksRestException {
    byte[] response = client
        .performQuery(RequestMethod.GET, "/libraries/all-cluster-statuses", null);
    Map<String, ClusterLibraryStatusesDTO[]> jsonObject = this.mapper.readValue(response, new
        TypeReference<Map<String, ClusterLibraryStatusesDTO[]>>() {
        });
    return jsonObject.get("statuses");
  }

  @Override
  public ClusterLibraryStatusesDTO clusterStatus(String clusterId)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    byte[] response = client.performQuery(RequestMethod.GET, "/libraries/cluster-status", data);
    return this.mapper.readValue(response, ClusterLibraryStatusesDTO.class);
  }

  @Override
  public void install(String clusterId, LibraryDTO[] libraries)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    data.put("libraries", libraries);

    client.performQuery(RequestMethod.POST, "/libraries/install", data);
  }

  @Override
  public void uninstall(String clusterId, LibraryDTO[] libraries)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("cluster_id", clusterId);
    data.put("libraries", libraries);

    client.performQuery(RequestMethod.POST, "/libraries/uninstall", data);
  }
}
