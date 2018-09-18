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

import com.edmunds.rest.databricks.DTO.DbfsReadDTO;
import com.edmunds.rest.databricks.DTO.FileInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestClient;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.RequestMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.net.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DbfsServiceImpl extends DatabricksService implements DbfsService {

  public DbfsServiceImpl(final DatabricksRestClient client) {
    super(client);
  }

  public void rm(String path, boolean recursive) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);
    data.put("recursive", recursive);

    client.performQuery(RequestMethod.POST, "/dbfs/delete", data);
  }

  public FileInfoDTO getInfo(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/dbfs/get-status", data);

    return mapper.readValue(responseBody, FileInfoDTO.class);
  }

  public FileInfoDTO[] ls(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/dbfs/list", data);

    Map<String, FileInfoDTO[]> jsonObject = this.mapper.readValue(responseBody, new TypeReference<Map<String, FileInfoDTO[]>>() {
    });
    return jsonObject.get("files");
  }

  public void mkdirs(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    client.performQuery(RequestMethod.POST, "/dbfs/mkdirs", data);
  }

  public void mv(String sourcePath, String destinationPath)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("source_path", sourcePath);
    data.put("destination_path", destinationPath);

    client.performQuery(RequestMethod.POST, "/dbfs/move", data);
  }

  public void write(String path, InputStream inputStream, boolean overwrite)
      throws IOException, DatabricksRestException {
    long handle = openHandle(path, overwrite);
    addBlocks(inputStream, handle);
  }

  public DbfsReadDTO read(String path, long offset, long length)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);
    data.put("offset", offset);
    data.put("length", length);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/dbfs/read", data);

    return this.mapper.readValue(responseBody, DbfsReadDTO.class);
  }

  public DbfsReadDTO read(String path) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);

    byte[] responseBody = client.performQuery(RequestMethod.GET, "/dbfs/read", data);

    return this.mapper.readValue(responseBody, DbfsReadDTO.class);
  }

  private long openHandle(String path, boolean overwrite)
      throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("path", path);
    data.put("overwrite", overwrite);

    byte[] responseBody = client.performQuery(RequestMethod.POST, "/dbfs/create", data);

    Map<String, Long> jsonObject = this.mapper.readValue(responseBody, new TypeReference<Map<String, Long>>() {
    });
    return jsonObject.get("handle");
  }

  private void closeHandle(long handle) throws IOException, DatabricksRestException {
    Map<String, Object> data = new HashMap<>();
    data.put("handle", handle);

    client.performQuery(RequestMethod.POST, "/dbfs/close", data);
  }

  private void addBlocks(InputStream inputStream, long handle)
      throws IOException, DatabricksRestException {
    if (handle != 0L) {
      byte[] buffer = new byte[1024 * 1024];
      int read;
      while ((read = inputStream.read(buffer)) > -1) {
        String data = Base64.encodeBase64String(Arrays.copyOf(buffer, read));
        Map<String, Object> params = new HashMap<>();
        params.put("handle", handle);
        params.put("data", data);
        client.performQuery(RequestMethod.POST, "/dbfs/add-block", params);
      }
      closeHandle(handle);
    }
  }
}