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

import com.edmunds.rest.databricks.DTO.unity.MetastoreDTO;
import com.edmunds.rest.databricks.DTO.unity.MetastoreUuidDTO;
import com.edmunds.rest.databricks.DTO.unity.WorkspaceIdDTO;
import com.edmunds.rest.databricks.DatabricksRestException;

import java.io.IOException;
import java.util.List;

/**
 * A Wrapper around the Unity Catalog part of the databricks rest api.
 *
 * @see <a href="https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html">https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html</a>
 */
public interface UnityCatalogService {

  /**
   * Return list of Unity Catalogs.
   *
   * @param accountId    Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @return list of Unity catalogs
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html">https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html</a>
   */
  List<MetastoreDTO> metastores(String accountId) throws IOException, DatabricksRestException;

  /**
   * Return list of Unity Catalogs.
   *
   * @param accountId    Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @param metastoreId  Unity Catalog ID
   * @return list of Unity catalogs
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   * @see <a href="https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html">https://api-docs.databricks.com/rest/latest/unity-catalog-api-specification-2-1.html</a>
   */
  MetastoreDTO metastore(String accountId, String metastoreId) throws IOException, DatabricksRestException;

  /**
   * Get list of IDs of Unity catalogs assigned with workspace.
   * The API is undocumented yet
   *
   * @param accountId Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @param workspaceId Workspace ID.
   * @return IDs of metastores
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  List<MetastoreUuidDTO> workspaceMetastoreId(String accountId, String workspaceId)
      throws IOException, DatabricksRestException;

  /**
   * Get list of IDs of workspaces that are assigned with metastore.
   * The API is undocumented yet
   *
   * @param accountId Databricks account ID of any type. For non-E2 account types, get your account ID from the <a href="https://docs.databricks.com/administration-guide/account-settings/usage.html">Accounts Console</a>
   * @param metastoreId Metastore ID.
   * @return IDs of workspaces
   * @throws IOException             other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  List<WorkspaceIdDTO> metastoreWorkspaceIds(String accountId, String metastoreId)
      throws IOException, DatabricksRestException;
}
