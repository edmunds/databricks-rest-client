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
import com.edmunds.rest.databricks.DTO.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.NewClusterDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import com.edmunds.rest.databricks.request.CreateClusterRequest;
import com.edmunds.rest.databricks.request.EditClusterRequest;
import java.io.IOException;
import java.util.List;

/**
 * A Wrapper around the cluster part of the databricks rest api.
 * @see <a href="https://docs.databricks.com/api/latest/clusters.html">https://docs.databricks.com/api/latest/clusters.html</a>
 */
public interface ClusterService {

  /**
   * Creates a databricks cluster.
   * @deprecated in version 2.3.2, please use {@link ClusterService#create(NewClusterDTO)}
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * @param createClusterRequest the cluster request object
   * @return the clusterId
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  @Deprecated
  String create(CreateClusterRequest createClusterRequest) throws IOException, DatabricksRestException;

  /**
   * Creates a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * @param clusterDTO cluster DTO
   * @return the clusterId
   * @throws IOException other connection errors
   * @throws DatabricksRestException any errors with the request
   */
  String create(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException;

  /**
   * Edits the configurations of a databricks cluster.
   * @deprecated in version 2.3.2, please use {@link ClusterService#edit(NewClusterDTO)}
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param editClusterRequest the edit cluster request object
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  @Deprecated
  void edit(EditClusterRequest editClusterRequest) throws IOException, DatabricksRestException;

  /**
   * Edits the configurations of a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param clusterDTO cluster DTO
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void edit(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException;

  /**
   * Starts a databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#start">https://docs.databricks.com/api/latest/clusters.html#start</a>
   * @param clusterId the clusterId of the cluster you want to start.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void start(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Restarts a given databricks cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#restart">https://docs.databricks.com/api/latest/clusters.html#restart</a>
   * @param clusterId the clusterid of the cluster you want to restart.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  void restart(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Resizes a cluster. Will give it a fixed number of workers.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#resize">https://docs.databricks.com/api/latest/clusters.html#resize</a>
   * @param numWorkers the number of workers you want to resize to
   * @param clusterId the clusterId you want to operate on
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void resize(int numWorkers, String clusterId) throws IOException, DatabricksRestException;

  /**
   * Resizes a cluster. Will add/edit autoscale functionality.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#resize">https://docs.databricks.com/api/latest/clusters.html#resize</a>
   * @param autoscale the autoscale specs.
   * @param clusterId the clusterId you want to work on
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void resize(AutoScaleDTO autoscale, String clusterId) throws IOException, DatabricksRestException;

  /**
   * Terminates a cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#delete-terminate">https://docs.databricks.com/api/latest/clusters.html#delete-terminate</a>
   * @param clusterId the cluster you want to terminate
   * @throws IOException any other errors
   * @throws DatabricksRestException errors with request
   */
  void delete(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Gets information about a given cluster.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#get">https://docs.databricks.com/api/latest/clusters.html#get</a>
   * @param clusterId the cluster you want to get info about
   * @return the information DTO object
   * @throws IOException any other errors.
   * @throws DatabricksRestException any errors with the request
   */
  ClusterInfoDTO getInfo(String clusterId) throws IOException, DatabricksRestException;

  /**
   * Lists all of the clusters on a given databricks instance.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#list">https://docs.databricks.com/api/latest/clusters.html#list</a>
   * @return an array of cluster information objects
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  ClusterInfoDTO[] list() throws IOException, DatabricksRestException;

  /**
   * Lists events of a specific cluster on a given databricks instance.
   * Allows you to filter which events you want to see too.
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#events">https://docs.databricks.com/api/latest/clusters.html#events</a>
   * @return an array of cluster events objects
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with the request
   */
  List<ClusterEventDTO> listEvents(String clusterId, ClusterEventTypeDTO[] eventsToFilter,
      int offset, int limit) throws IOException,
      DatabricksRestException;

  /**
   * Given a cluster settings DTO object it will:
   * - create the cluster if it doesn't exist
   * - edit the cluster if it does exist.
   * Uses a combination of
   * If cluster doesn't exist:
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#create">https://docs.databricks.com/api/latest/clusters.html#create</a>
   * If cluster exists:
   * @see <a href="https://docs.databricks.com/api/latest/clusters.html#edit">https://docs.databricks.com/api/latest/clusters.html#edit</a>
   * @param clusterDTO cluster DTO
   */
  void upsertCluster(NewClusterDTO clusterDTO) throws IOException, DatabricksRestException;
}
