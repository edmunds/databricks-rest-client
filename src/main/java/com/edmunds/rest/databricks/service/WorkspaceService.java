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
import com.edmunds.rest.databricks.request.ExportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import java.io.IOException;

/**
 *
 */
public interface WorkspaceService {

  void delete(String path, boolean recursive) throws IOException, DatabricksRestException;

  byte[] exportWorkspace(ExportWorkspaceRequest exportWorkspaceRequest)
      throws IOException, DatabricksRestException;

  ObjectInfoDTO getStatus(String path) throws IOException, DatabricksRestException;

  void importWorkspace(ImportWorkspaceRequest importWorkspaceRequest)
      throws IOException, DatabricksRestException;

  ObjectInfoDTO[] listStatus(String path) throws IOException, DatabricksRestException;

  void mkdirs(String path) throws IOException, DatabricksRestException;

}
