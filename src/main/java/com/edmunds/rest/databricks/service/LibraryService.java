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
import java.io.IOException;

/**
 * The wrapper around the databricks Library API.
 * https://docs.databricks.com/api/latest/libraries.html
 */
public interface LibraryService {

  /**
   * Gets the statuses of all clusters on a databricks instance.
   * https://docs.databricks.com/api/latest/libraries.html#all-cluster-statuses
   * @return the array of all cluster statuses.
   * @throws IOException any other errors.
   * @throws DatabricksRestException errors with db request.
   */
  ClusterLibraryStatusesDTO[] allClusterStatuses() throws IOException, DatabricksRestException;

  /**
   * Gets the status of a specific cluster.
   * https://docs.databricks.com/api/latest/libraries.html#cluster-status
   * @param clusterId the cluster id to get the status of
   * @return the cluster status dto object
   * @throws IOException any other errors
   * @throws DatabricksRestException errors with specific request
   */
  ClusterLibraryStatusesDTO clusterStatus(String clusterId)
      throws IOException, DatabricksRestException;

  /**
   * Installs one or more libraries on a specific cluster.
   * https://docs.databricks.com/api/latest/libraries.html#install
   * @param clusterId the cluster to install to.
   * @param libraries the libraries to install to this clsuter
   * @throws IOException any other errors
   * @throws DatabricksRestException errors with specific request
   */
  void install(String clusterId, LibraryDTO[] libraries)
      throws IOException, DatabricksRestException;

  /**
   * Uninstalls one or more libraries from a specific cluster.
   * https://docs.databricks.com/api/latest/libraries.html#uninstall
   * @param clusterId the cluster to uninstall a library from
   * @param libraries the libraries to uninstall
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void uninstall(String clusterId, LibraryDTO[] libraries)
      throws IOException, DatabricksRestException;
}
