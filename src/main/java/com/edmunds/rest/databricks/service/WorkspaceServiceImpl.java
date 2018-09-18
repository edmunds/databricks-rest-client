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

import com.edmunds.rest.databricks.DTO.ObjectInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.edmunds.rest.databricks.request.ExportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import com.edmunds.rest.databricks.restclient.DatabricksRestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Workspace Service.
 */
public class WorkspaceServiceImpl extends DatabricksService implements WorkspaceService {

  public WorkspaceServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  @Override
  public void delete(String path, boolean recursive) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);
    data.put("recursive", recursive);

    client.performQuery(RequestMethod.POST, "/workspace/delete", data);
  }

  @Override
  public byte[] exportWorkspace(ExportWorkspaceRequest exportWorkspaceRequest) throws IOException,
      DatabricksRestException {
    byte[] responseBody = client
        .performQuery(RequestMethod.GET, "/workspace/export", exportWorkspaceRequest.getData());
    Map<String, String> result = mapper
        .readValue(responseBody, new TypeReference<Map<String, String>>() {
        });

    return result.get("content").getBytes();
  }

  @Override
  public ObjectInfoDTO getStatus(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/workspace/get-status", data);
    return mapper.readValue(responseBody, ObjectInfoDTO.class);
  }

  public void importWorkspace(ImportWorkspaceRequest importWorkspaceRequest) throws IOException,
      DatabricksRestException {
    client.performQuery(RequestMethod.POST, "/workspace/import", importWorkspaceRequest.getData());
  }

  @Override
  public ObjectInfoDTO[] listStatus(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/workspace/list", data);

    Map<String, ObjectInfoDTO[]> result = mapper
        .readValue(responseBody, new TypeReference<Map<String,
            ObjectInfoDTO[]>>() {
        });
    return result.get("objects");
  }

  @Override
  public void mkdirs(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    client.performQuery(RequestMethod.POST, "/workspace/mkdirs", data);
  }

}