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

import com.edmunds.rest.databricks.DTO.workspace.ObjectInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.request.ExportWorkspaceRequest;
import com.edmunds.rest.databricks.request.ImportWorkspaceRequest;
import java.io.IOException;

/**
 * Wrapper around the Worspace API.
 * @see <a href="https://docs.databricks.com/api/latest/workspace.html">https://docs.databricks.com/api/latest/workspace.html</a>
 */
public interface WorkspaceService {

  /**
   * Deletes a specific workspace path.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#delete">https://docs.databricks.com/api/latest/workspace.html#delete</a>
   * @param path the workspace path to delete
   * @param recursive whether or not its recursive
   * @throws IOException any other errors
   * @throws DatabricksRestException databricks specific errors
   */
  void delete(String path, boolean recursive) throws IOException, DatabricksRestException;

  /**
   * Exports a workspace path FROM databricks to local machine.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#export">https://docs.databricks.com/api/latest/workspace.html#export</a>
   * @param exportWorkspaceRequest the request object
   * @return the bytes of the export
   * @throws IOException any other errors
   * @throws DatabricksRestException any specific db errors
   */
  byte[] exportWorkspace(ExportWorkspaceRequest exportWorkspaceRequest)
      throws IOException, DatabricksRestException;

  /**
   * Gets the status of a specific workspace path.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#get-status">https://docs.databricks.com/api/latest/workspace.html#get-status</a>
   * @param path the workspace path
   * @return the workspace info object
   * @throws IOException any other errors
   * @throws DatabricksRestException any specific db errors
   */
  ObjectInfoDTO getStatus(String path) throws IOException, DatabricksRestException;

  /**
   * Imports files INTO databricks from local machine.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#import">https://docs.databricks.com/api/latest/workspace.html#import</a>
   * @param importWorkspaceRequest the import workspace request
   * @throws IOException any other errors
   * @throws DatabricksRestException specific db exceptions
   */
  void importWorkspace(ImportWorkspaceRequest importWorkspaceRequest)
      throws IOException, DatabricksRestException;

  /**
   * Lists all objects in a specific db workspace path.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#list">https://docs.databricks.com/api/latest/workspace.html#list</a>
   * @param path the db workspace path
   * @return the array of objects
   * @throws IOException any other errors
   * @throws DatabricksRestException specific db exceptions
   */
  ObjectInfoDTO[] listStatus(String path) throws IOException, DatabricksRestException;

  /**
   * Makes a path (and any parent paths if they do not exist) on db workspace.
   * @see <a href="https://docs.databricks.com/api/latest/workspace.html#mkdirs">https://docs.databricks.com/api/latest/workspace.html#mkdirs</a>
   * @param path the db workspace path to create
   * @throws IOException any other errors
   * @throws DatabricksRestException any specific db exceptions
   */
  void mkdirs(String path) throws IOException, DatabricksRestException;

}
